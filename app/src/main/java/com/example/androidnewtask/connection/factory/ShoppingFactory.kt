package com.example.androidnewtask.connection.factory

import com.example.androidnewtask.model.request.HomeBody
import com.example.androidnewtask.model.response.HomeResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ShoppingFactory {

    @POST("home")
    fun getHomePage(
        @Body homeBody: HomeBody
    ) : Call<HomeResponse>
}