package com.example.androidnewtask.ui.fragments.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.androidnewtask.R
import com.example.androidnewtask.model.request.HomeBody
import com.example.androidnewtask.model.response.HomeResponse
import com.example.androidnewtask.ui.adapters.BannersAdapter
import com.example.androidnewtask.ui.adapters.CategoriesAdapter
import com.example.androidnewtask.ui.adapters.ProductsAdapter
import com.example.androidnewtask.ui.fragments.base.BaseFragment
import com.example.androidnewtask.utils.PermissionUtils
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var bannersAdapter: BannersAdapter
    private lateinit var productsAdapter: ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeToRefresh.setColorSchemeResources(R.color.purple_700)

        searchView.queryHint = context?.resources?.getString(R.string.search_hint)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        viewModel.showProgress.observe(viewLifecycleOwner, Observer {
            if (it) {
                showLoading()
            } else {
                hideLoading()
            }
        })

        swipeToRefresh.setOnRefreshListener {
            getCurrentLocationName()
        }

        viewModel.homeResponse.observe(viewLifecycleOwner, Observer {
            Log.i(TAG, it.commonContent.categories.toString())
            bindViews(it)
        })

        setAdapters()

    }

    private fun getCurrentLocationName() {
        when {
            PermissionUtils.isAccessFineLocationGranted(context) -> {
                when {
                    PermissionUtils.isLocationEnabled(context) -> {
                        setUpLocationListener()
                        viewModel.getHomePage(HomeBody(2, 1))
                    }
                    else -> {
                        PermissionUtils.showGPSNotEnabledDialog(context)
                    }
                }
            }
            else -> {
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    private fun setAdapters() {
        //Categories Adapter
        categoriesAdapter = CategoriesAdapter(context)
        rvCategories.adapter = categoriesAdapter

        //Newest Product Adapter
        productsAdapter = ProductsAdapter(context)
        rvNewestProducts.adapter = productsAdapter
        rvTopSales.adapter = productsAdapter
    }

    private fun bindViews(homeResponse: HomeResponse?) {
        categoriesAdapter.setCategoriesList(homeResponse?.commonContent?.categories)
        productsAdapter.setProductList(homeResponse?.products?.product_data)
        productsAdapter.setProductList(homeResponse?.top_seller?.product_data)
        bannersAdapter = BannersAdapter(context, homeResponse?.commonContent?.homeBanners)
        viewPagerImageSlider.adapter = bannersAdapter
        circle_indicator.setViewPager(viewPagerImageSlider)
        if (homeResponse?.commonContent?.homeBanners?.size == 1)
            circle_indicator.visibility = View.GONE
        else
            circle_indicator.visibility = View.VISIBLE

        swipeToRefresh.isRefreshing = false
    }

    @SuppressLint("MissingPermission")
    private fun setUpLocationListener() {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        // for getting the current location update after every 2 seconds with high accuracy
        val locationRequest = LocationRequest().setInterval(2000).setFastestInterval(2000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    for (location in locationResult.locations) {
                        tvLocationName?.text =
                            context?.let {
                                getCountryName(
                                    it,
                                    location.latitude,
                                    location.longitude
                                )
                            }
                    }
                }
            },
            Looper.myLooper()
        )
    }

    override fun onStart() {
        super.onStart()
        getCurrentLocationName()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    when {
                        context?.let { PermissionUtils.isLocationEnabled(it) } == true -> {
                            setUpLocationListener()
                        }
                        else -> {
                            context?.let { PermissionUtils.showGPSNotEnabledDialog(it) }
                        }
                    }
                } else {
                    Toast.makeText(
                        context,
                        getString(R.string.location_permission_not_granted),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    companion object {
        private const val TAG = "HomeFragment"
        private const val LOCATION_PERMISSION_REQUEST_CODE = 999
    }


}