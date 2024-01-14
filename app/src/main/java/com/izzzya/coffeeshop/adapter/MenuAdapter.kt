package com.izzzya.coffeeshop.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.izzzya.coffeeshop.R
import com.izzzya.coffeeshop.api.response.CoffeeItem
import com.izzzya.coffeeshop.classes.OrderItem
import com.izzzya.coffeeshop.classes.SessionManager
import com.squareup.picasso.Picasso


class MenuAdapter(private val context: Context,
                  private val dataset: List<CoffeeItem>
): RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    class MenuViewHolder(view: View): RecyclerView.ViewHolder(view!!){
        val IV = view.findViewById<ImageView>(R.id.coffeeIV)
        val nameTV = view.findViewById<TextView>(R.id.itemNameTV)
        val priceTV = view.findViewById<TextView>(R.id.itemPriceTV)
        val plusBtn = view.findViewById<ImageButton>(R.id.plusBtn)
        val minusBtn = view.findViewById<ImageButton>(R.id.minusBtn)
        val qtyTV = view.findViewById<TextView>(R.id.quantityTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val mLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.coffeemenu_item, parent, false)
        return MenuViewHolder(mLayout)
    }

    override fun getItemCount(): Int = dataset.size

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = dataset[position]
        var quant = 0
        val imgURL = item.imageUrl
        Picasso.get().load(imgURL).into(holder.IV)
        fun itemInCart() = SessionManager.orderList.contains(SessionManager.orderList.find { it.name == item.name })


        holder.nameTV.text = item.name
        holder.priceTV.text = item.price.toString() + context.getString(R.string.rub)
        if (itemInCart()){
            quant = SessionManager.orderList.find { it.name == item.name }!!.quantity
            holder.qtyTV.text = quant.toString()

        }

        //чек делаем
        holder.plusBtn.setOnClickListener {
            quant++
            holder.qtyTV.text = quant.toString()
            if (itemInCart()){
                SessionManager.orderList.find { it.name == item.name }?.quantity = quant
            }else{
                SessionManager.orderList.add(OrderItem(
                    item.id,
                    item.name,
                    item.imageUrl,
                    item.price,
                    quant
                ))
            }
        }
        holder.minusBtn.setOnClickListener {
            if (quant>1){
                quant--
                holder.qtyTV.text = quant.toString()
                SessionManager.orderList.find { it.name == item.name }?.quantity = quant
            }else if (quant==1) {
                quant--
                holder.qtyTV.text = quant.toString()
                SessionManager.orderList.remove(SessionManager.orderList.find { it.name == item.name })
            }

        }

    }




}