package com.zoomerbox.data

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth
import com.zoomerbox.data.provider.local.room.AppDatabase
import com.zoomerbox.data.provider.local.room.dao.ZoomerBoxDao
import com.zoomerbox.model.entity.ZoomerBoxEntity
import org.junit.After
import org.junit.Before
import org.junit.Test

class ZoomerBoxDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var zoomerBoxDao: ZoomerBoxDao

    @Before
    @Throws(Exception::class)
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java
        )
            .build()
        zoomerBoxDao = db.zoomerBoxDao()
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAllThenGetTheSameOnes() {
        val boxes = listOf("a", "b", "c").map { name ->
            ZoomerBoxEntity(
                0,
                name,
                "",
                "",
                listOf(""),
                listOf(1, 2, 3)
            )
        }
        zoomerBoxDao.insertAll(boxes)
        val result = zoomerBoxDao.getAll()
        Truth.assertThat(result.map { zoomerBox -> zoomerBox.name })
            .isEqualTo(boxes.map { zoomerBox -> zoomerBox.name })
    }

    @Test
    fun replaceOneIfDuplicate() {
        val boxes = listOf("a", "b", "c").map { name ->
            ZoomerBoxEntity(
                0,
                name,
                "",
                "",
                listOf(""),
                listOf(1, 2, 3)
            )
        }
        zoomerBoxDao.insertAll(boxes)
        val inserted = zoomerBoxDao.getAll()
        val id = inserted[0].id
        inserted[0].name = "d"
        zoomerBoxDao.insertAll(inserted)
        Truth.assertThat(zoomerBoxDao.getById(id).name).isEqualTo("d")
    }

    @Test
    fun deleteSuccessfully() {
        val boxes = listOf("a", "b", "c").map { name ->
            ZoomerBoxEntity(
                0,
                name,
                "",
                "",
                listOf(""),
                listOf(1, 2, 3)
            )
        }
        zoomerBoxDao.insertAll(boxes)
        Truth.assertThat(zoomerBoxDao.getAll()).isNotEmpty()
        zoomerBoxDao.deleteAll()
        Truth.assertThat(zoomerBoxDao.getAll()).isEmpty()
    }
}
