package com.example.androidnewtask.ui.fragments.home

import androidx.lifecycle.MutableLiveData
import com.example.androidnewtask.MyApplication
import com.example.androidnewtask.model.request.HomeBody
import com.example.androidnewtask.model.response.HomeResponse
import com.example.androidnewtask.utils.Logger.Logger.debugErrorLogMessage
import com.example.androidnewtask.utils.Logger.Logger.debugSuccessLogMessage
import com.example.androidnewtask.utils.NetworkState.NetworkState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragmentRepository {

    val homeResponse = MutableLiveData<HomeResponse>()
    val showProgress = MutableLiveData<Boolean>()
    val networkState = MutableLiveData<NetworkState>()

    fun getHomePage(homeBody: HomeBody) {
        showProgress.value = true
        MyApplication.restControllerFactory.getShoppingFactory()?.getHomePage(homeBody)?.enqueue(
            object : Callback<HomeResponse> {
                override fun onResponse(
                    call: Call<HomeResponse>,
                    response: Response<HomeResponse>
                ) {

                    if (response.isSuccessful) {
                        homeResponse.value = response.body()
                        debugSuccessLogMessage("Home page been received successfully")
                        networkState.postValue(NetworkState.LOADED)
                    } else {
                        debugErrorLogMessage(response.message())
                        networkState.postValue(NetworkState.ERROR)
                    }
                    showProgress.value = false

                }

                override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                    debugErrorLogMessage(t.message.toString())
                    showProgress.value = false
                    networkState.postValue(NetworkState.ERROR)
                }

            }
        )

    }


}