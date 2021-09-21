package com.zoomerbox.data.repository.impl

import android.content.SharedPreferences
import com.zoomerbox.data.provider.local.room.dao.*
import com.zoomerbox.data.repository.ICacheRepository
import com.zoomerbox.model.app.*
import com.zoomerbox.model.app.Collection
import com.zoomerbox.model.entity.BoxItemEntity
import com.zoomerbox.model.entity.CollectionEntity
import com.zoomerbox.model.entity.SeasonDropEntity
import com.zoomerbox.model.entity.ZoomerBoxEntity
import io.reactivex.Single
import javax.inject.Inject

class CacheRepository @Inject constructor(
    private val boxItemDao: BoxItemDao,
    private val collectionDao: CollectionDao,
    private val seasonDropDao: SeasonDropDao,
    private val zoomerBoxDao: ZoomerBoxDao,
    private val preferences: SharedPreferences
) : ICacheRepository {

    override fun isCacheEnabled(): Boolean {
        return preferences.getBoolean("cache_feature", false)
    }

    override fun clearCache(): Single<Boolean> {
        return Single.fromCallable {
            boxItemDao.deleteAll()
            collectionDao.deleteAll()
            seasonDropDao.deleteAll()
            zoomerBoxDao.deleteAll()
            true
        }
    }

    override fun getSeasonDrop(): Single<SeasonDrop> {
        return Single.fromCallable {
            val seasonDropEntity = seasonDropDao.getSeasonDrop()
            SeasonDrop(
                seasonDropEntity.seasonNumber,
                Banner(seasonDropEntity.seasonBillboardImageUrl),
                getCollections(seasonDropEntity.collectionIds)
            )
        }
    }

    private fun getCollections(collectionIds: List<Long>): List<Collection> {
        return collectionIds.map { collectionId ->
            val collectionEntity = collectionDao.getById(collectionId)
            Collection(collectionEntity.collectionName, getBoxes(collectionEntity.zoomerBoxIds))
        }
    }

    private fun getBoxes(boxIds: List<Long>): List<ZoomerBox> {
        return boxIds.map { boxId ->
            val boxEntity = zoomerBoxDao.getById(boxId)
            ZoomerBox(
                boxEntity.name,
                boxEntity.price,
                boxEntity.imageUrls,
                boxEntity.description,
                getBoxItems(boxEntity.boxItemsIds)
            )
        }
    }

    private fun getBoxItems(boxItemIds: List<Long>): List<BoxItem> {
        return boxItemIds.map { boxItemId ->
            val boxItemEntity = boxItemDao.getById(boxItemId)
            BoxItem(
                boxItemEntity.name,
                boxItemEntity.imageUrls,
                boxItemEntity.description
            )
        }
    }

    override fun saveDrop(seasonDrop: SeasonDrop): Single<Long> {
        return Single.fromCallable {
            seasonDropDao.insert(
                SeasonDropEntity(
                    0,
                    seasonDrop.seasonNumber,
                    seasonDrop.banner.imageUrl,
                    saveCollections(seasonDrop)
                )
            )
        }
    }

    private fun saveCollections(seasonDrop: SeasonDrop): List<Long> {
        return collectionDao.insertAll(seasonDrop.collections.map { collection ->
            CollectionEntity(
                0,
                collection.collectionName,
                saveBoxes(collection)
            )
        })
    }

    private fun saveBoxes(collection: Collection): List<Long> {
        return zoomerBoxDao.insertAll(collection.boxes.map { box ->
            ZoomerBoxEntity(
                0,
                box.name,
                box.description,
                box.price,
                box.imageUrls,
                saveBoxItems(box)
            )
        })
    }

    private fun saveBoxItems(box: ZoomerBox): List<Long> {
        return boxItemDao.insertAll(box.items.map { item ->
            BoxItemEntity(
                0,
                item.name,
                item.description,
                item.imageUrls
            )
        })
    }
}
