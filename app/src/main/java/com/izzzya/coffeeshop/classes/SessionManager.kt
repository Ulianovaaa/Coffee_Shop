package com.izzzya.coffeeshop.classes

import android.content.Context
import android.content.SharedPreferences
import com.izzzya.coffeeshop.api.response.CoffeeItem
import com.izzzya.coffeeshop.api.response.Location

class SessionManager(context: Context) {

    init {
        sharedPref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)

    }

    companion object{
        private var sharedPref: SharedPreferences? = null
        const val PREFERENCES = "prefs"
        const val TOKEN = "TOKEN"
        const val TOKEN_LIFETIME = "TOKEN_LIFETIME"
        var shopId = 0
        val orderList: MutableList<OrderItem> = mutableListOf()
        var locationsList = listOf<Location>()



        fun getToken(): String {
            return "" + sharedPref?.getString(TOKEN, null)
        }

        fun setToken(tokenType: String, token: String) {
            val editor = sharedPref?.edit()
            editor?.putString(TOKEN, "$tokenType $token")
            editor?.apply()
        }

        fun getTokenExpires(): Long {
            return if(sharedPref != null)
                sharedPref!!.getLong(TOKEN_LIFETIME, 0)
            else 0L
        }

        fun setTokenExpires(expires: Long) {
            val editor = sharedPref?.edit()
            editor?.putLong(TOKEN_LIFETIME, expires)
            editor?.apply()
        }
    }
}