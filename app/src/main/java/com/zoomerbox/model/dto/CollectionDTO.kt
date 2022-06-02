package com.zoomerbox.model.dto

import kotlinx.android.parcel.RawValue

/**
 *
 * DTO с данными коллекции (категории) боксов
 */
data class CollectionDTO(
    val collectionName: String,
    val boxes: @RawValue List<ZoomerBoxDTO>
)
