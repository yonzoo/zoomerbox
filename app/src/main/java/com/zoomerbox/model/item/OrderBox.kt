package com.zoomerbox.model.item

import android.os.Parcelable
import com.zoomerbox.model.dto.OrderBoxDTO
import kotlinx.android.parcel.Parcelize

@Parcelize
class OrderBox(
    val box: ZoomerBox,
    val count: Int,
    val isFavourite: Boolean
) : Parcelable {

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