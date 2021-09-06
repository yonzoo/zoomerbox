package com.zoomerbox.data.repository

import com.zoomerbox.data.exception.RequestFailedException
import com.zoomerbox.model.dto.SeasonDropDTO

interface ISeasonDropRepository {

    @Throws(RequestFailedException::class)
    fun getSeasonDrop(): SeasonDropDTO

    fun getImplName(): String
}
