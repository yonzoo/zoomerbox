package com.zoomerbox.data.repository.impl

import com.zoomerbox.data.repository.IFavouriteRepository
import com.zoomerbox.data.service.FavouriteApiService
import com.zoomerbox.model.app.ZoomerBox
import io.reactivex.Single
import javax.inject.Inject

class FavouriteRepository @Inject constructor(
    private val favouriteApiService: FavouriteApiService
) : IFavouriteRepository {

    override fun getFavouriteItems(uid: String): Single<List<ZoomerBox>> {
        return favouriteApiService.getFavouriteBoxes(uid).map { zoomerBoxDTOs ->
            zoomerBoxDTOs.map { boxDTO -> ZoomerBox.buildFromDTO(boxDTO) }
        }
    }

    override fun isBoxFavourite(uid: String, boxName: String): Single<Boolean> {
        return favouriteApiService.isBoxFavourite(uid, boxName)
    }

    override fun toggleFavouriteBox(uid: String, boxName: String): Single<Boolean> {
        return favouriteApiService.toggleFavouriteBox(uid, boxName)
    }

    override fun removeBoxFromFavourite(uid: String, boxName: String): Single<List<ZoomerBox>> {
        return favouriteApiService.removeBoxFromFavourite(uid, boxName)
    }

    override fun getImplName(): String {
        return this::class.java.simpleName
    }
}