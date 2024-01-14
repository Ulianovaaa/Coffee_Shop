package com.izzzya.coffeeshop.api.response

import com.google.gson.annotations.SerializedName

class Location {
    @SerializedName("id")
    var id: Int = 0
    @SerializedName("name")
    var name: String = ""
    @SerializedName("point")
    var point: Point? = null
}