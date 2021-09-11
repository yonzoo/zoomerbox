package com.zoomerbox.model.item

import android.os.Parcelable
import com.zoomerbox.model.dto.ZoomerBoxDTO
import kotlinx.android.parcel.Parcelize

@Parcelize
class ZoomerBoxItem(
    val name: String,
    val price: String,
    val imageUrls: List<String>,
    val description: String,
    val items: List<BoxItemItem>,
    val possibleRareItems: List<BoxItemItem>
) : Parcelable {

    companion object {

        fun buildFromDTO(zoomerBoxDTO: ZoomerBoxDTO): ZoomerBoxItem {
            return ZoomerBoxItem(
                zoomerBoxDTO.name,
                zoomerBoxDTO.price,
                zoomerBoxDTO.imageUrls,
                zoomerBoxDTO.description,
                zoomerBoxDTO.items.map { itemDTO -> BoxItemItem.buildFromDTO(itemDTO) },
                zoomerBoxDTO.possibleRareItems.map { itemDTO -> BoxItemItem.buildFromDTO(itemDTO) }
            )
        }
    }
}