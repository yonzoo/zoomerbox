package com.zoomerbox.data.repository

import com.zoomerbox.data.exception.RequestFailedException
import com.zoomerbox.model.app.Order
import com.zoomerbox.model.app.OrderBox

interface IOrdersRepository {

    @Throws(RequestFailedException::class)
    fun getOrders(): List<Order>

    fun createOrder(
        cityName: String,
        fullName: String,
        postIndex: String,
        orderItems: List<OrderBox>
    ): Order

    fun getImplName(): String
}