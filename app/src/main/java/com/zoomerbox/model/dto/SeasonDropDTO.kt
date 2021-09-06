package com.zoomerbox.model.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class SeasonDropDTO(
    val seasonNumber: Int,
    val seasonName: String,
    val seasonBillboardImageUrl: String,
    val collections: @RawValue List<CollectionDTO>
) : Parcelable
