package com.zoomerbox.data.repository

import com.zoomerbox.data.exception.RequestFailedException
import com.zoomerbox.model.app.ZoomerBox

interface IFavouriteRepository {

    @Throws(RequestFailedException::class)
    fun getFavouriteItems(): List<ZoomerBox>

    fun getImplName(): String
}