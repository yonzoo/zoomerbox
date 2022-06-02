package com.zoomerbox.model.dto

import kotlinx.android.parcel.RawValue

/**
 *
 * DTO с содержимым бокса
 *
 * @param name имя бокса
 * @param price цена бокса
 * @param imageUrls список с изображениями бокса
 * @param description описание бокса
 * @param items список с предметами бокса
 */
data class ZoomerBoxDTO(
    val name: String,
    val price: String,
    val imageUrls: List<String>,
    val description: @RawValue String,
    val items: @RawValue List<BoxItemDTO>)
