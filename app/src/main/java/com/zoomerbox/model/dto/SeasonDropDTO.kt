package com.zoomerbox.model.dto

import kotlinx.android.parcel.RawValue

data class SeasonDropDTO(
    val seasonNumber: Int,
    val seasonBillboardImageUrl: String,
    val collections: @RawValue List<CollectionDTO>
)
