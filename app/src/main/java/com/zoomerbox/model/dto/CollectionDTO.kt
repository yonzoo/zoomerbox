package com.zoomerbox.model.dto

import kotlinx.android.parcel.RawValue

data class CollectionDTO(
    val collectionName: String,
    val boxes: @RawValue List<ZoomerBoxDTO>
)
