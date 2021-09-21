package com.zoomerbox.data.provider.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zoomerbox.model.entity.CollectionEntity

@Dao
interface CollectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(collectionsEntities: List<CollectionEntity>): List<Long>

    @Query("SELECT * FROM CollectionEntity WHERE id = :id")
    fun getById(id: Long): CollectionEntity

    @Query("DELETE FROM CollectionEntity")
    fun deleteAll()
}
