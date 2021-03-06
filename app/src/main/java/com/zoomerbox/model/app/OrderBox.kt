package com.zoomerbox.model.app

import android.os.Parcelable
import com.zoomerbox.model.dto.OrderBoxDTO
import kotlinx.android.parcel.Parcelize

@Parcelize
class OrderBox(
    val box: ZoomerBox,
    val count: Int
) : Parcelable {

    fun toDTO(): OrderBoxDTO {
        return OrderBoxDTO(box.toDTO(), count)
    }

    companion object {

        fun buildFromDTO(orderBoxDTO: OrderBoxDTO): OrderBox {
            return OrderBox(
                ZoomerBox.buildFromDTO(orderBoxDTO.box),
                orderBoxDTO.count
            )
        }
    }
}