package com.izzzya.coffeeshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import com.izzzya.coffeeshop.classes.SessionManager
import com.yandex.mapkit.MapKitFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SessionManager(this)
        //при новом заходе корзина чистится это чтобы красиво было
        SessionManager.orderList.clear()
        //ключ к апи яндекс карт
        MapKitFactory.setApiKey("c253fd21-6ade-40ed-b1d4-b9067a59b502")
        setContentView(R.layout.activity_main)

        val host = findViewById<FragmentContainerView>(R.id.fragmentContainerView)

        findViewById<ImageButton>(R.id.backIB).setOnClickListener{
            host.findNavController().popBackStack()
        }
    }
}