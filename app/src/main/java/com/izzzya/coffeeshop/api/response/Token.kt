package com.izzzya.coffeeshop.api.response

import com.google.gson.annotations.SerializedName

class Token {
    @SerializedName("token")
    var token: String = ""
    @SerializedName("tokenType")
    var tokenType: String = ""
    @SerializedName("tokenLifetime")
    var tokenLifetime: Long = 0L
}