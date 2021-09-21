package com.zoomerbox.data

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth
import com.zoomerbox.data.provider.local.room.AppDatabase
import com.zoomerbox.data.provider.local.room.dao.SeasonDropDao
import com.zoomerbox.model.entity.SeasonDropEntity
import org.junit.After
import org.junit.Before
import org.junit.Test

class SeasonDropDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var seasonDropDao: SeasonDropDao

    @Before
    @Throws(Exception::class)
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java
        )
            .build()
        seasonDropDao = db.seasonDropDao()
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertThenGetTheSameOne() {
        val seasonDrop = SeasonDropEntity(
            0,
            1,
            "",
            listOf(1, 2, 3)
        )
        seasonDropDao.insert(seasonDrop)
        val result = seasonDropDao.getSeasonDrop()
        Truth.assertThat(seasonDrop.seasonNumber)
            .isEqualTo(result.seasonNumber)
    }

    @Test
    fun replaceOneIfDuplicate() {
        val seasonDrop = SeasonDropEntity(
            0,
            1,
            "",
            listOf(1, 2, 3)
        )
        seasonDropDao.insert(seasonDrop)
        val inserted = seasonDropDao.getSeasonDrop()
        inserted.seasonNumber = 2
        seasonDropDao.insert(inserted)
        Truth.assertThat(seasonDropDao.getSeasonDrop().seasonNumber).isEqualTo(2)
    }

    @Test
    fun deleteSuccessfully() {
        val seasonDrop = SeasonDropEntity(
            0,
            1,
            "",
            listOf(1, 2, 3)
        )
        seasonDropDao.insert(seasonDrop)
        Truth.assertThat(seasonDropDao.getSeasonDrop()).isNotNull()
        seasonDropDao.deleteAll()
        Truth.assertThat(seasonDropDao.getSeasonDrop()).isNull()
    }
}