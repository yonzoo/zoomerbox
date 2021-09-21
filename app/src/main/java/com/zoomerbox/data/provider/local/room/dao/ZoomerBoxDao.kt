package com.zoomerbox.data.provider.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zoomerbox.model.entity.ZoomerBoxEntity

@Dao
interface ZoomerBoxDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(zoomerBoxes: List<ZoomerBoxEntity>): List<Long>

    @Query("SELECT * FROM ZoomerBoxEntity")
    fun getAll(): List<ZoomerBoxEntity>

    @Query("SELECT * FROM ZoomerBoxEntity WHERE id = :id")
    fun getById(id: Long): ZoomerBoxEntity

    @Query("DELETE FROM ZoomerBoxEntity")
    fun deleteAll()
}
