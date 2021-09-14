package com.zoomerbox.data.repository

import com.zoomerbox.data.exception.RequestFailedException
import com.zoomerbox.model.item.SeasonDrop

interface ISeasonDropRepository {

    @Throws(RequestFailedException::class)
    fun getSeasonDrop(): SeasonDrop

    fun getImplName(): String
}
