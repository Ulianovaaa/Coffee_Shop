package com.izzzya.coffeeshop.api

import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param
import com.izzzya.coffeeshop.api.response.CoffeeItem
import com.izzzya.coffeeshop.api.response.Location
import com.izzzya.coffeeshop.api.response.Token
import com.izzzya.coffeeshop.classes.currentUser
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryName

interface NetworkAPI {
    //login
    @POST("/auth/login")
    @Headers("Accept: application/json")
    fun login(
        @Body login: currentUser
    ): Call<Token>

    //reg
    @POST("/auth/register")
    @Headers("Accept: application/json")
    fun register(
        @Body reg: currentUser
    ): Call<Token>

    //shops
    @GET("/locations")
    @Headers("Accept: application/json")
    fun getLocs(
        @Header("Authorization") token: String
    ): Call <List<Location>>

    //menu
    @GET("/location/{id}/menu")
    @Headers("Accept: application/json")
    fun getMenu(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call <List<CoffeeItem>>


}