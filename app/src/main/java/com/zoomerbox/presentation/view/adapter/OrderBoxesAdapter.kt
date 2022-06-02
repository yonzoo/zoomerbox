package com.zoomerbox.presentation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zoomerbox.R
import com.zoomerbox.databinding.ItemOrderBoxBinding
import com.zoomerbox.model.app.OrderBox

/**
 * Адаптер, который умеет отображать список боксов из заказа
 *
 * @param orderBoxes список боксов из заказа
 */
class OrderBoxesAdapter(
    private var orderBoxes: List<OrderBox>
) : RecyclerView.Adapter<OrderBoxesAdapter.OrderBoxViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderBoxViewHolder {
        context = parent.context
        return OrderBoxViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_order_box, parent, false)
        )
    }

    override fun onBindViewHolder(holder: OrderBoxViewHolder, position: Int) {
        holder.bind(orderBoxes[position])
    }

    override fun getItemCount(): Int {
        return orderBoxes.size
    }

    fun setData(orderList: List<OrderBox>) {
        this.orderBoxes = orderList
    }

    fun getData(): List<OrderBox> {
        return orderBoxes
    }

    inner class OrderBoxViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var itemBinding: ItemOrderBoxBinding =
            ItemOrderBoxBinding.bind(itemView)

        fun bind(orderBox: OrderBox) {
            itemBinding.boxTitle.text = orderBox.box.name
            itemBinding.itemAmount.text =
                context.resources.getString(R.string.item_nice_amount, orderBox.count.toString())
            if (orderBox.box.imageUrls.isNotEmpty()) {
                Picasso.get().load(orderBox.box.imageUrls[0]).into(itemBinding.boxItemPreview)
            }
        }
    }
}
