package com.zoomerbox.data.repository.impl

import com.zoomerbox.data.repository.IOrdersRepository
import com.zoomerbox.data.service.OrdersApiService
import com.zoomerbox.model.app.Order
import com.zoomerbox.model.app.OrderBox
import io.reactivex.Single
import javax.inject.Inject

class OrdersRepository @Inject constructor(
    private val ordersApiService: OrdersApiService
) : IOrdersRepository {

    override fun getOrders(uid: String): Single<List<Order>> {
        return ordersApiService.getOrders(uid)
            .map { orders -> orders.map { orderDTO -> Order.buildFromDTO(orderDTO) } }
    }

    override fun createOrder(
        uid: String,
        cityName: String,
        fullName: String,
        postIndex: String,
        orderItems: List<OrderBox>
    ): Single<Order> {
        return ordersApiService.createOrder(
            uid,
            cityName,
            fullName,
            postIndex,
            orderItems.map { orderItem -> orderItem.toDTO() })
            .map { orderDTO -> Order.buildFromDTO(orderDTO) }
    }

    override fun getImplName(): String {
        return this::class.java.simpleName
    }
}