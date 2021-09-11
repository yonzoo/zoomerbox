package com.zoomerbox.model.item

import android.os.Parcelable
import com.zoomerbox.model.dto.BoxItemDTO
import com.zoomerbox.model.enumeration.RarenessEnum
import kotlinx.android.parcel.Parcelize

@Parcelize
class BoxItemItem(
    val name: String,
    val imageUrls: List<String>,
    val description: String,
    val rareness: RarenessEnum
) : Parcelable {

    companion object {

        fun buildFromDTO(boxItemDTO: BoxItemDTO): BoxItemItem {
            return BoxItemItem(
                boxItemDTO.name,
                boxItemDTO.imageUrls,
                boxItemDTO.description,
                boxItemDTO.rareness
            )
        }
    }
}