package com.example.androidnewtask.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.androidnewtask.model.request.HomeBody
import com.example.androidnewtask.model.response.HomeResponse
import com.example.androidnewtask.utils.NetworkState.NetworkState

class HomeViewModel : ViewModel() {

    private val repository = HomeFragmentRepository()
    val homeResponse: LiveData<HomeResponse>
    val networkState: LiveData<NetworkState>

    val showProgress: LiveData<Boolean>

    init {
        this.showProgress = repository.showProgress
        this.homeResponse = repository.homeResponse
        this.networkState = repository.networkState
    }

    fun getHomePage(homeBody: HomeBody) {
        repository.getHomePage(homeBody)
    }

}