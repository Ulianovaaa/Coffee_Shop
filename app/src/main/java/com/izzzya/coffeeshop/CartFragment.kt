package com.izzzya.coffeeshop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.izzzya.coffeeshop.adapter.CartAdapter
import com.izzzya.coffeeshop.adapter.CoffeeShopsAdapter
import com.izzzya.coffeeshop.classes.SessionManager


class CartFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cartRV = view.findViewById<RecyclerView>(R.id.orderRV)
        val llm = LinearLayoutManager(requireContext())
        llm.orientation = LinearLayoutManager.VERTICAL
        cartRV.layoutManager = llm
        cartRV.adapter = CartAdapter(requireContext(), SessionManager.orderList)

        view.findViewById<Button>(R.id.payBtn).setOnClickListener{
            view.findViewById<TextView>(R.id.msgTV).text = getString(R.string.msg_15)

        }
    }

}