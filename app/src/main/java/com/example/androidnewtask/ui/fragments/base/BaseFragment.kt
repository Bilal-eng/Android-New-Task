package com.example.androidnewtask.ui.fragments.base

import android.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.androidnewtask.utils.CommonUtils

abstract class BaseFragment: Fragment() {

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
}