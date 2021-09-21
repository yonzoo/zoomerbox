package com.zoomerbox.data

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth
import com.zoomerbox.data.provider.local.room.AppDatabase
import com.zoomerbox.data.provider.local.room.dao.CollectionDao
import com.zoomerbox.data.utils.MockDataProvider
import com.zoomerbox.model.entity.CollectionEntity
import org.junit.After
import org.junit.Before
import org.junit.Test

class CollectionDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var collectionDao: CollectionDao

    @Before
    @Throws(Exception::class)
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java
        )
            .build()
        collectionDao = db.collectionDao()
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAllThenGetTheSameOnes() {
        val collections = MockDataProvider.getCollections().map { collection ->
            CollectionEntity(
                0,
                collection.collectionName,
                listOf(1, 2, 3)
            )
        }
        collectionDao.insertAll(collections)
        val result = collectionDao.getAll()
        Truth.assertThat(result.map { collection -> collection.collectionName })
            .isEqualTo(collections.map { collection -> collection.collectionName })
    }

    @Test
    fun replaceOneIfDuplicate() {
        val collections = listOf("a", "b", "c").map { name ->
            CollectionEntity(
                0,
                name,
                listOf(1, 2, 3)
            )
        }
        collectionDao.insertAll(collections)
        val inserted = collectionDao.getAll()
        val id = inserted[0].id
        inserted[0].collectionName = "d"
        collectionDao.insertAll(inserted)
        Truth.assertThat(collectionDao.getById(id).collectionName).isEqualTo("d")
    }

    @Test
    fun deleteSuccessfully() {
        val collections = listOf("a", "b", "c").map { name ->
            CollectionEntity(
                0,
                name,
                listOf(1, 2, 3)
            )
        }
        collectionDao.insertAll(collections)
        Truth.assertThat(collectionDao.getAll()).isNotEmpty()
        collectionDao.deleteAll()
        Truth.assertThat(collectionDao.getAll()).isEmpty()
    }
}
