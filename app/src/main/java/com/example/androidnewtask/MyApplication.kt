package com.example.androidnewtask

import android.app.Application
import com.example.androidnewtask.connection.RestControllerFactory

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initializeApi()
    }

    private fun initializeApi() {
        restControllerFactory = RestControllerFactory(this)
        appContext = this
    }

    companion object {

        lateinit var restControllerFactory: RestControllerFactory
        lateinit var appContext: MyApplication
    }

}