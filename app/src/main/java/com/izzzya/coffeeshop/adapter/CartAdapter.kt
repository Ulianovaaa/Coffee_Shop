package com.izzzya.coffeeshop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.izzzya.coffeeshop.R
import com.izzzya.coffeeshop.classes.OrderItem
import com.izzzya.coffeeshop.classes.SessionManager

class CartAdapter(private val context: Context,
                  private val dataset: List<OrderItem>
): RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(view: View): RecyclerView.ViewHolder(view!!){
        val name = view.findViewById<TextView>(R.id.orderItemName)
        val price = view.findViewById<TextView>(R.id.orderItemPrice)
        val quantity = view.findViewById<TextView>(R.id.quantityTVOrder)
        val plus = view.findViewById<ImageButton>(R.id.plusBtnOrder)
        val minus = view.findViewById<ImageButton>(R.id.minusBtnOrder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val mLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_item, parent, false)
        return CartViewHolder(mLayout)
    }

    override fun getItemCount(): Int = dataset.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = dataset[position]
        var quant = 0
        val itemInCart = SessionManager.orderList.contains(item)


        holder.name.text = item.name
        holder.price.text = item.price.toString() + context.getString(R.string.rub)
        if (itemInCart){
            quant = SessionManager.orderList.find { it.name == item.name }!!.quantity
            holder.quantity.text = quant.toString()

        }
        holder.plus.setOnClickListener {
            quant++
            holder.quantity.text = quant.toString()
            if (itemInCart){
                SessionManager.orderList.find { it.name == item.name }?.quantity = quant
            }else{
                SessionManager.orderList.add(
                    OrderItem(
                    item.id,
                    item.name,
                    item.imageUrl,
                    item.price,
                    quant
                )
                )
            }
        }
        holder.minus.setOnClickListener {
            if (quant>1){
                quant--
                holder.quantity.text = quant.toString()
                SessionManager.orderList.find { it.name == item.name }?.quantity = quant
            }else{
                SessionManager.orderList.remove(item)
                holder.itemView.visibility = View.GONE
            }

        }
    }


}