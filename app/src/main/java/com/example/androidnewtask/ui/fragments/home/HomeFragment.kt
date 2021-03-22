package com.example.androidnewtask.ui.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.androidnewtask.R
import com.example.androidnewtask.model.request.HomeBody
import com.example.androidnewtask.model.response.HomeResponse
import com.example.androidnewtask.ui.adapters.BannersAdapter
import com.example.androidnewtask.ui.adapters.CategoriesAdapter
import com.example.androidnewtask.ui.adapters.ProductsAdapter
import com.example.androidnewtask.ui.fragments.base.BaseFragment
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
            viewModel.getHomePage(HomeBody(2, 1))
        }

        viewModel.getHomePage(HomeBody(2, 1))

        viewModel.homeResponse.observe(viewLifecycleOwner, Observer {
            Log.i(TAG, it.commonContent.categories.toString())
            bindViews(it)
        })

        setAdapters()

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

    companion object {
        private const val TAG = "HomeFragment"
    }

}