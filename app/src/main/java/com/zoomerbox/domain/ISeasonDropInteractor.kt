package com.zoomerbox.domain

import com.zoomerbox.data.utils.exception.RequestFailedException
import com.zoomerbox.model.app.SeasonDrop
import io.reactivex.Single

interface ISeasonDropInteractor {

    @Throws(RequestFailedException::class)
    fun getSeasonDrop(): Single<SeasonDrop>
}