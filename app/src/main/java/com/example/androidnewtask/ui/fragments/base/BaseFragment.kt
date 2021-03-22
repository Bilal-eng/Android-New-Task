package com.example.androidnewtask.ui.fragments.base

import android.app.AlertDialog
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.androidnewtask.utils.CommonUtils
import java.io.IOException
import java.util.*

abstract class BaseFragment : Fragment() {

    private var mProgressDialog: AlertDialog? = null

    fun showLoading() {
        hideLoading()
        mProgressDialog = context?.let { CommonUtils.showLoadingDialog(it) }
    }

    fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog?.isShowing!!) {
            mProgressDialog?.cancel()
        }
    }

    fun getCountryName(context: Context, latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses: List<Address>?
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses != null && addresses.isNotEmpty()) {
                return addresses[0].countryName
            }
        } catch (exception: IOException) {
            Log.e(TAG, exception.stackTrace.toString())
        }
        return "Dubai"
    }

    companion object {
        private const val TAG = "BaseFragment"
    }
}