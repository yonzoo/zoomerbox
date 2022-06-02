package com.zoomerbox.presentation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zoomerbox.R
import com.zoomerbox.databinding.ItemOrdersBinding
import com.zoomerbox.domain.util.StatusResolver
import com.zoomerbox.model.app.Order

/**
 * Адаптер, который умеет отображать список заказов
 *
 * @param orderList список заказов
 */
class OrdersAdapter(
    private var orderList: List<Order>
) : RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        context = parent.context
        return OrderViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_orders, parent, false)
        )
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orderList[position])
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    fun setData(orderList: List<Order>) {
        this.orderList = orderList
    }

    fun getData(): List<Order> {
        return orderList
    }

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var itemBinding: ItemOrdersBinding =
            ItemOrdersBinding.bind(itemView)

        fun bind(order: Order) {
            itemBinding.orderNumber.text = order.orderNumber
            itemBinding.orderTitle.text =
                context.resources.getString(R.string.order_by_date, order.date)
            itemBinding.orderStatus.text = StatusResolver.resolveOrderStatus(order.orderStatus)
            itemBinding.totalOrderSum.text =
                context.resources.getString(R.string.money_amount, order.totalOrderSum.toString())
            itemBinding.orderBoxes.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            itemBinding.orderBoxes.adapter = OrderBoxesAdapter(order.boxes)
        }
    }
}
