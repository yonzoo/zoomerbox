package com.zoomerbox.model.dto

import kotlinx.android.parcel.RawValue

data class BoxItemDTO(
    val name: String,
    val imageUrls: @RawValue List<String>,
    val description: String
)
