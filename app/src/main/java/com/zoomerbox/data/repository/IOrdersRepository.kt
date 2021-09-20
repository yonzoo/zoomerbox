package com.zoomerbox.data.repository

import com.zoomerbox.data.exception.RequestFailedException
import com.zoomerbox.model.app.Order
import com.zoomerbox.model.app.OrderBox
import io.reactivex.Single

interface IOrdersRepository {

    @Throws(RequestFailedException::class)
    fun getOrders(uid: String): Single<List<Order>>

    fun createOrder(
        uid: String,
        cityName: String,
        fullName: String,
        postIndex: String,
        orderItems: List<OrderBox>
    ): Single<Order>

    fun getImplName(): String
}