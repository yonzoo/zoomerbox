package com.zoomerbox.data.repository

import com.zoomerbox.data.exception.RequestFailedException
import com.zoomerbox.model.item.Order

interface IOrdersRepository {

    @Throws(RequestFailedException::class)
    fun getOrders(): List<Order>

    fun getImplName(): String
}