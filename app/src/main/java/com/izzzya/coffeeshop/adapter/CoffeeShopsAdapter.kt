package com.izzzya.coffeeshop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.izzzya.coffeeshop.R
import com.izzzya.coffeeshop.api.response.Location
import com.izzzya.coffeeshop.classes.SessionManager

class CoffeeShopsAdapter(private val context: Context,
                         private val dataset: List<Location>
): RecyclerView.Adapter<CoffeeShopsAdapter.LocationsViewHolder>() {

    class LocationsViewHolder(view: View): RecyclerView.ViewHolder(view!!){
        val locationNameTV = view.findViewById<TextView>(R.id.shopNameTV)
        val locationDistance = view.findViewById<TextView>(R.id.distanceTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder {
        val mLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.coffeeshop_item, parent, false)
        return LocationsViewHolder(mLayout)
    }

    override fun getItemCount(): Int = dataset.size

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) {
        val loc = dataset[position]
        holder.locationNameTV.text = loc.name
        holder.locationDistance.text = loc.point!!.latitude.toString() +" "+ loc.point!!.longitude.toString()

        holder.itemView.setOnClickListener { p0 ->
            SessionManager.shopId = loc.id
            p0!!.findNavController().navigate(R.id.action_global_menuFragment)
        }
    }

}