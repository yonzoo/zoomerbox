package com.zoomerbox.data.repository

import com.zoomerbox.data.exception.RequestFailedException
import com.zoomerbox.model.app.SeasonDrop
import io.reactivex.Single

interface ISeasonDropRepository {

    @Throws(RequestFailedException::class)
    fun getSeasonDrop(): Single<SeasonDrop>

    fun getImplName(): String
}
