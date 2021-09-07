package com.zoomerbox.data.repository

import com.zoomerbox.data.exception.RequestFailedException
import com.zoomerbox.model.dto.SeasonDropDTO
import com.zoomerbox.model.item.SeasonDropItem

interface ISeasonDropRepository {

    @Throws(RequestFailedException::class)
    fun getSeasonDrop(): SeasonDropItem

    fun getImplName(): String
}
