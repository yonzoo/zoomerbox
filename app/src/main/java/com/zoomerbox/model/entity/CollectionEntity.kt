package com.zoomerbox.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CollectionEntity(
    @PrimaryKey(autoGenerate = true) var id: Long,
    var collectionName: String,
    var zoomerBoxIds: List<Long>
)
