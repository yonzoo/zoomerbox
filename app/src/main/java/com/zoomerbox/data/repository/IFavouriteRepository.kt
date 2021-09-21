package com.zoomerbox.data.repository

import com.zoomerbox.data.utils.exception.RequestFailedException
import com.zoomerbox.model.app.ZoomerBox
import io.reactivex.Single

interface IFavouriteRepository {

    @Throws(RequestFailedException::class)
    fun getFavouriteItems(uid: String): Single<List<ZoomerBox>>

    fun isBoxFavourite(uid: String, boxName: String): Single<Boolean>

    fun toggleFavouriteBox(uid: String, boxName: String): Single<Boolean>

    fun removeBoxFromFavourite(uid: String, boxName: String): Single<List<ZoomerBox>>

    fun getImplName(): String
}