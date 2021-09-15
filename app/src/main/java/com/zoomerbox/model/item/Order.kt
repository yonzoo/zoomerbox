package com.zoomerbox.model.item

import com.zoomerbox.model.dto.OrderDTO
import com.zoomerbox.model.enumeration.OrderStatusEnum

class Order(
    val boxes: List<OrderBox>,
    val date: String,
    val orderNumber: String,
    val orderStatus: OrderStatusEnum,
    val totalOrderSum: Int
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