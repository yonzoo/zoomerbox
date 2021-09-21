package com.zoomerbox.data.provider.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zoomerbox.model.entity.BoxItemEntity

@Dao
interface BoxItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(boxItems: List<BoxItemEntity>): List<Long>

    @Query("SELECT * FROM BoxItemEntity WHERE id = :id")
    fun getById(id: Long): BoxItemEntity

    @Query("DELETE FROM BoxItemEntity")
    fun deleteAll()
}
