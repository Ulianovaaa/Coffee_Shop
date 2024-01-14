package com.izzzya.coffeeshop.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object NetworkService {

    private const val BASE_URL = "http://147.78.66.203:3210"

    private val mInstance: NetworkService = NetworkService
    private var mRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun getInstanceRetrofit(): NetworkService {
        return mInstance
    }

    fun getJSONApi(): NetworkAPI {
        return mRetrofit.create(NetworkAPI::class.java)
    }

}