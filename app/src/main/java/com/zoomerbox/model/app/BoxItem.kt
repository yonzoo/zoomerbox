package com.zoomerbox.model.app

import android.os.Parcelable
import com.zoomerbox.model.dto.BoxItemDTO
import kotlinx.android.parcel.Parcelize

@Parcelize
class BoxItem(
    val name: String,
    val imageUrls: List<String>,
    val description: String
) : Parcelable {

    fun toDTO(): BoxItemDTO {
        return BoxItemDTO(
            name,
            imageUrls,
            description
        )
    }

    companion object {

        fun buildFromDTO(boxItemDTO: BoxItemDTO): BoxItem {
            return BoxItem(
                boxItemDTO.name,
                boxItemDTO.imageUrls,
                boxItemDTO.description
            )
        }
    }
}
