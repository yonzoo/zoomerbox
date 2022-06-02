package com.zoomerbox.model.app

import android.os.Parcelable
import com.zoomerbox.model.dto.ZoomerBoxDTO
import kotlinx.android.parcel.Parcelize

/**
 *
 * Модель с содержимым бокса
 *
 * @param name имя бокса
 * @param price цена бокса
 * @param imageUrls список с изображениями бокса
 * @param description описание бокса
 * @param items список с предметами бокса
 */
@Parcelize
class ZoomerBox(
    val name: String,
    val price: String,
    val imageUrls: List<String>,
    val description: String,
    val items: List<BoxItem>
) : Parcelable {

    fun toDTO(): ZoomerBoxDTO {
        return ZoomerBoxDTO(
            name,
            price,
            imageUrls,
            description,
            items.map { item -> item.toDTO() }
        )
    }

    companion object {

        fun buildFromDTO(zoomerBoxDTO: ZoomerBoxDTO): ZoomerBox {
            return ZoomerBox(
                zoomerBoxDTO.name,
                zoomerBoxDTO.price,
                zoomerBoxDTO.imageUrls,
                zoomerBoxDTO.description,
                zoomerBoxDTO.items.map { itemDTO -> BoxItem.buildFromDTO(itemDTO) })
        }
    }
}
