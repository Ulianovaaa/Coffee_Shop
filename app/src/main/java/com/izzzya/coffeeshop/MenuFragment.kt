package com.izzzya.coffeeshop

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.izzzya.coffeeshop.adapter.CoffeeShopsAdapter
import com.izzzya.coffeeshop.adapter.MenuAdapter
import com.izzzya.coffeeshop.api.NetworkService
import com.izzzya.coffeeshop.api.response.CoffeeItem
import com.izzzya.coffeeshop.api.response.Location
import com.izzzya.coffeeshop.classes.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MenuFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<TextView>(R.id.headerTV).text = getString(R.string.menu)


        fun getMenu() {
            NetworkService.getInstanceRetrofit().getJSONApi()
            .getMenu(
                SessionManager.getToken(), SessionManager.shopId.toString())
            .enqueue(object: Callback<List<CoffeeItem>> {
                override fun onResponse(call: Call<List<CoffeeItem>>, response: Response<List<CoffeeItem>>) {
                    if(response.isSuccessful || response.code() == 200){
                        if(response.body() == null) {
                            Toast.makeText(requireContext(),"null response", Toast.LENGTH_SHORT).show()
                        }
                        val menuRV = view.findViewById<RecyclerView>(R.id.menuRV)
                        val glm = GridLayoutManager(requireContext(), 2)
                        //glm.orientation = LinearLayoutManager.VERTICAL
                        menuRV.layoutManager = glm
                        menuRV.adapter = MenuAdapter(requireContext(), response.body()!!)

                    } else if((response.code() == 400)){
                        Toast.makeText(requireContext(),"400", Toast.LENGTH_SHORT).show()
                    }else if((response.code() == 401)){
                        Toast.makeText(requireContext(),"401", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<List<CoffeeItem>>, t: Throwable) {
                    t.message?.let { Log.println(Log.INFO, "ON FAIL", it) }
                    Toast.makeText(requireContext(),"connection err", Toast.LENGTH_SHORT).show()
                }
            })
}

        getMenu()
        view.findViewById<Button>(R.id.goPayBtn).setOnClickListener{
            findNavController().navigate(R.id.action_global_cartFragment)
        }


    }

}