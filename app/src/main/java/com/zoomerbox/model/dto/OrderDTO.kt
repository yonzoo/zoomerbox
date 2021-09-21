package com.zoomerbox.model.dto

import com.zoomerbox.model.util.enumeration.OrderStatusEnum

data class OrderDTO(
    val boxes: List<OrderBoxDTO>,
    val date: String,
    val orderNumber: String,
    val orderStatus: OrderStatusEnum,
    val totalOrderSum: Int
)
