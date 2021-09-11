package com.zoomerbox.model.dto

import com.zoomerbox.model.enumeration.RarenessEnum
import kotlinx.android.parcel.RawValue

data class BoxItemDTO(
    val name: String,
    val imageUrls: @RawValue List<String>,
    val description: String,
    val rareness: RarenessEnum
)
