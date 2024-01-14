package com.izzzya.coffeeshop

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.izzzya.coffeeshop.adapter.CoffeeShopsAdapter
import com.izzzya.coffeeshop.api.NetworkService
import com.izzzya.coffeeshop.api.response.Location
import com.izzzya.coffeeshop.classes.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShopsFragment : Fragment() {

    var source: List<Location> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shops, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<TextView>(R.id.headerTV).text = "Кофейни"

        fun getLocations() {
            NetworkService.getInstanceRetrofit().getJSONApi()
                .getLocs(
                    SessionManager.getToken())
                .enqueue(object: Callback<List<Location>> {
                    override fun onResponse(call: Call<List<Location>>, response: Response<List<Location>>) {
                        if(response.isSuccessful || response.code() == 200){
                            //если нет ответа
                            if(response.body() == null) {
//                            val e = java.lang.Exception(getString(R.string.ExcNoResponse))
                                Toast.makeText(requireContext(),"null response", Toast.LENGTH_SHORT).show()
                            }
                            SessionManager.locationsList = response.body()!!
                            val rv = view.findViewById<RecyclerView>(R.id.shopsRV)
                            val llm = LinearLayoutManager(requireContext())
                            llm.orientation = LinearLayoutManager.VERTICAL
                            rv.layoutManager = llm
                            rv.adapter = CoffeeShopsAdapter(requireContext(), response.body()!!)

                        } else if((response.code() == 400)){
                            Toast.makeText(requireContext(),"400", Toast.LENGTH_SHORT).show()
                        }else if((response.code() == 401)){
                            Toast.makeText(requireContext(),"401", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<List<Location>>, t: Throwable) {
                        t.message?.let { Log.println(Log.INFO, "ON FAIL", it) }
                        Toast.makeText(requireContext(),"connection err", Toast.LENGTH_SHORT).show()
                    }
                })
        }

        getLocations()

        view.findViewById<Button>(R.id.seeMapBtn).setOnClickListener{
            findNavController().navigate(R.id.action_global_mapFragment)
        }

    }

    companion object{
        var RESPONSE: List<Location> = listOf()
    }


}