package com.zoomerbox.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
 * Entity с содержимым бокса
 */
@Entity
data class ZoomerBoxEntity(
    @PrimaryKey(autoGenerate = true) var id: Long,
    var name: String,
    var description: String,
    var price: String,
    var imageUrls: List<String>,
    var boxItemsIds: List<Long>
)
