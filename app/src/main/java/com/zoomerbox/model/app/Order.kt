package com.zoomerbox.model.app

import com.zoomerbox.model.dto.OrderDTO
import com.zoomerbox.model.util.enumeration.OrderStatusEnum

/**
 *
 * Модель с данными заказа
 *
 * @param boxes список боксов в заказе
 * @param date дата заказа
 * @param orderNumber номер заказа
 * @param orderStatus статус заказа
 * @param totalOrderSum сумма заказа
 */
class Order(
    val boxes: List<OrderBox>,
    val date: String,
    val orderNumber: String,
    val orderStatus: OrderStatusEnum,
    var totalOrderSum: Int
) {

    companion object {

        fun buildFromDTO(orderDTO: OrderDTO): Order {
            return Order(
                orderDTO.boxes.map { orderBoxDTO -> OrderBox.buildFromDTO(orderBoxDTO) },
                orderDTO.date,
                orderDTO.orderNumber,
                orderDTO.orderStatus,
                orderDTO.totalOrderSum
            )
        }
    }
}