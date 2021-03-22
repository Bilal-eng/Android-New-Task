package com.example.androidnewtask.connection

import android.content.Context
import com.example.androidnewtask.connection.factory.ShoppingFactory
import com.example.androidnewtask.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RestControllerFactory(context: Context) {

    var retrofit: Retrofit? = null
    private val timeoutInterval = 60
    private val client: OkHttpClient
    private var shoppingFactory: ShoppingFactory?

    val DEBUG_MODE = true


    init {
        val logging = HttpLoggingInterceptor()

        if (DEBUG_MODE) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.NONE
        }
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor(logging)
        httpClient.connectTimeout(timeoutInterval.toLong(), TimeUnit.SECONDS)
        httpClient.readTimeout(timeoutInterval.toLong(), TimeUnit.SECONDS)
        httpClient.addInterceptor { chain ->
            val original = chain.request()

            val request = original.newBuilder()
                .method(original.method(), original.body())
                .build()

            chain.proceed(request)
        }


        client = httpClient.build()
        retrofit = Retrofit.Builder()
            .baseUrl(Constants.API_ADDRESS)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        shoppingFactory = retrofit?.create(ShoppingFactory::class.java)

    }

    fun getShoppingFactory(): ShoppingFactory? {
        return shoppingFactory
    }

}