package com.zoomerbox.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SeasonDropEntity(
    @PrimaryKey(autoGenerate = true) var id: Long,
    var seasonNumber: Int,
    var seasonBillboardImageUrl: String,
    var collectionIds: List<Long>
)
