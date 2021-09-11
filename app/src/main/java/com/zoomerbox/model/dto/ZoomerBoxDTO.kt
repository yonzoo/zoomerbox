package com.zoomerbox.model.dto

import kotlinx.android.parcel.RawValue

data class ZoomerBoxDTO(
    val name: String,
    val price: String,
    val imageUrls: List<String>,
    val description: @RawValue String,
    val items: @RawValue List<BoxItemDTO>,
    val possibleRareItems: @RawValue List<BoxItemDTO>
)
