package com.zoomerbox.model.item

import com.zoomerbox.model.dto.OrderBoxDTO
import com.zoomerbox.model.dto.ZoomerBoxDTO

class OrderBox(
    val box: ZoomerBox,
    val count: Int,
    val isFavourite: Boolean
) {

    companion object {

        fun buildFromDTO(orderBoxDTO: OrderBoxDTO): OrderBox {
            return OrderBox(
                ZoomerBox.buildFromDTO(orderBoxDTO.box),
                orderBoxDTO.count,
                orderBoxDTO.isFavourite
            )
        }
    }
}