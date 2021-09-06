package com.zoomerbox.model.dto

import android.os.Parcelable
import com.zoomerbox.model.enumeration.RarenessEnum
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class BoxItemDTO(
    val name: String,
    val imageUrls: @RawValue List<String>,
    val description: String,
    val rareness: RarenessEnum
) : Parcelable
