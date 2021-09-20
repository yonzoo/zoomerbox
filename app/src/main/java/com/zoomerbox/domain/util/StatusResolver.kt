package com.zoomerbox.domain.util

import com.zoomerbox.model.enumeration.OrderStatusEnum

object StatusResolver {

    fun resolveOrderStatus(orderStatus: OrderStatusEnum): String {
        return when (orderStatus) {
            OrderStatusEnum.CREATED -> "Заказ создан"
            OrderStatusEnum.PACKAGED -> "Готово к отправке"
            OrderStatusEnum.SENT -> "Заказ отправлен"
            OrderStatusEnum.FINISHED -> "Заказ прибыл"
        }
    }
}