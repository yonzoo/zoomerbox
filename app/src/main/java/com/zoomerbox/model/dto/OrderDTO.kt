package com.zoomerbox.model.dto

import com.zoomerbox.model.util.enumeration.OrderStatusEnum

/**
 *
 * DTO с данными заказа
 *
 * @param boxes список боксов в заказе
 * @param date дата заказа
 * @param orderNumber номер заказа
 * @param orderStatus статус заказа
 * @param totalOrderSum сумма заказа
 */
data class OrderDTO(
    val boxes: List<OrderBoxDTO>,
    val date: String,
    val orderNumber: String,
    val orderStatus: OrderStatusEnum,
    val totalOrderSum: Int
)
