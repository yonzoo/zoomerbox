package com.zoomerbox.model.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class ZoomerBoxDTO(
    val name: String,
    val imageUrls: List<String>,
    val description: @RawValue String,
    val items: @RawValue List<BoxItemDTO>,
    val rareItems: @RawValue List<BoxItemDTO>
) : Parcelable
