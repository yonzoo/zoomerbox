package com.zoomerbox.data.repository

import com.zoomerbox.model.app.SeasonDrop
import io.reactivex.Single

interface ICacheRepository {

    fun clearCache(): Single<Boolean>

    fun saveDrop(seasonDrop: SeasonDrop): Single<Long>

    fun getSeasonDrop(): Single<SeasonDrop>

    fun isCacheEnabled(): Boolean
}
