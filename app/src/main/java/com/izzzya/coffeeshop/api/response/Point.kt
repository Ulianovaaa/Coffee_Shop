package com.izzzya.coffeeshop.api.response

import com.google.gson.annotations.SerializedName

class Point {
    @SerializedName("latitude")
    var latitude: Double = 0.0
    @SerializedName("longitude")
    var longitude: Double = 0.0
}