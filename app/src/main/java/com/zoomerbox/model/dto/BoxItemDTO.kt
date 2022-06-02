package com.zoomerbox.model.dto

import kotlinx.android.parcel.RawValue

/**
 *
 * DTO с деталями предмета внутри бокса
 */
data class BoxItemDTO(
    val name: String,
    val imageUrls: @RawValue List<String>,
    val description: String
)
