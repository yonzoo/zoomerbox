package com.zoomerbox.model.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class CollectionDTO(
    val collectionName: String,
    val boxes: @RawValue List<ZoomerBoxDTO>
) : Parcelable
