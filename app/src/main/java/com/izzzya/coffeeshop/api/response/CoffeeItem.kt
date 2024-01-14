package com.izzzya.coffeeshop.api.response

import com.google.gson.annotations.SerializedName

class CoffeeItem {
    @SerializedName("id")
    var id: Int = 0
    @SerializedName("name")
    var name: String = ""
    @SerializedName("imageURL")
    var imageUrl: String = ""
    @SerializedName("price")
    var price: Int = 0
}