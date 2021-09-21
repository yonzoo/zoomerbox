package com.zoomerbox.data

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth
import com.zoomerbox.data.provider.local.room.AppDatabase
import com.zoomerbox.data.provider.local.room.dao.BoxItemDao
import com.zoomerbox.data.utils.MockDataProvider
import com.zoomerbox.model.entity.BoxItemEntity
import org.junit.After
import org.junit.Before
import org.junit.Test

class BoxItemDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var boxItemDao: BoxItemDao

    @Before
    @Throws(Exception::class)
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java
        )
            .build()
        boxItemDao = db.boxItemDao()
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAllThenGetTheSameOnes() {
        val boxItems = MockDataProvider.getBoxes(listOf("a", "b", "c")).map { box ->
            BoxItemEntity(
                0,
                box.name,
                box.description,
                box.imageUrls
            )
        }
        boxItemDao.insertAll(boxItems)
        val result = boxItemDao.getAll()
        Truth.assertThat(result.map { box -> box.name }).isEqualTo(boxItems.map { box -> box.name })
    }

    @Test
    fun replaceOneIfDuplicate() {
        val boxItems = MockDataProvider.getBoxes(listOf("a", "b", "c")).map { box ->
            BoxItemEntity(
                0,
                box.name,
                box.description,
                box.imageUrls
            )
        }
        boxItemDao.insertAll(boxItems)
        val inserted = boxItemDao.getAll()
        val id = inserted[0].id
        inserted[0].name = "d"
        boxItemDao.insertAll(inserted)
        Truth.assertThat(boxItemDao.getById(id).name).isEqualTo("d")
    }

    @Test
    fun deleteSuccessfully() {
        val boxItems = MockDataProvider.getBoxes(listOf("a", "b", "c")).map { box ->
            BoxItemEntity(
                0,
                box.name,
                box.description,
                box.imageUrls
            )
        }
        boxItemDao.insertAll(boxItems)
        Truth.assertThat(boxItemDao.getAll()).isNotEmpty()
        boxItemDao.deleteAll()
        Truth.assertThat(boxItemDao.getAll()).isEmpty()
    }
}
