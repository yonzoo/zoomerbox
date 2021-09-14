package com.zoomerbox.model.item

import android.os.Parcelable
import com.zoomerbox.model.dto.ZoomerBoxDTO
import kotlinx.android.parcel.Parcelize

@Parcelize
class ZoomerBox(
    val name: String,
    val price: String,
    val imageUrls: List<String>,
    val description: String,
    val items: List<BoxItem>,
    val possibleRareItems: List<BoxItem>
) : Parcelable {

    companion object {

        fun buildFromDTO(zoomerBoxDTO: ZoomerBoxDTO): ZoomerBox {
            return ZoomerBox(
                zoomerBoxDTO.name,
                zoomerBoxDTO.price,
                zoomerBoxDTO.imageUrls,
                zoomerBoxDTO.description,
                zoomerBoxDTO.items.map { itemDTO -> BoxItem.buildFromDTO(itemDTO) },
                zoomerBoxDTO.possibleRareItems.map { itemDTO -> BoxItem.buildFromDTO(itemDTO) }
            )
        }
    }
}
