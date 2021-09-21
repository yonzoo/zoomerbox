package com.zoomerbox.data.provider.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zoomerbox.model.entity.SeasonDropEntity

@Dao
interface SeasonDropDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(seasonDropEntity: SeasonDropEntity): Long

    @Query("SELECT * FROM SeasonDropEntity")
    fun getSeasonDrop(): SeasonDropEntity

    @Query("DELETE FROM SeasonDropEntity")
    fun deleteAll()
}
