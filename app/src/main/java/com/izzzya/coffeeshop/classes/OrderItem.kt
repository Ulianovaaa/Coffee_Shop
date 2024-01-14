package com.izzzya.coffeeshop.classes

import com.google.gson.annotations.SerializedName

data class OrderItem(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val price: Int,
    var quantity: Int,
) {


}