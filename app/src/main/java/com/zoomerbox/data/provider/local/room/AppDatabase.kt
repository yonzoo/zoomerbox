package com.zoomerbox.data.provider.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zoomerbox.data.provider.local.room.converter.Converters
import com.zoomerbox.data.provider.local.room.dao.BoxItemDao
import com.zoomerbox.data.provider.local.room.dao.CollectionDao
import com.zoomerbox.data.provider.local.room.dao.SeasonDropDao
import com.zoomerbox.data.provider.local.room.dao.ZoomerBoxDao
import com.zoomerbox.model.entity.BoxItemEntity
import com.zoomerbox.model.entity.CollectionEntity
import com.zoomerbox.model.entity.SeasonDropEntity
import com.zoomerbox.model.entity.ZoomerBoxEntity

@Database(
    entities = [
        BoxItemEntity::class,
        CollectionEntity::class,
        SeasonDropEntity::class,
        ZoomerBoxEntity::class
    ], version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun boxItemDao(): BoxItemDao
    abstract fun collectionDao(): CollectionDao
    abstract fun seasonDropDao(): SeasonDropDao
    abstract fun zoomerBoxDao(): ZoomerBoxDao
}