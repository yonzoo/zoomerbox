package com.zoomerbox.domain.impl

import android.content.Context
import android.content.SharedPreferences
import com.zoomerbox.data.repository.impl.CacheRepository
import com.zoomerbox.data.repository.impl.SeasonDropRepository
import com.zoomerbox.domain.ISeasonDropInteractor
import com.zoomerbox.domain.util.ConfigurationChecker
import com.zoomerbox.model.app.SeasonDrop
import io.reactivex.Single
import javax.inject.Inject


class SeasonDropInteractor @Inject constructor(
    private val seasonDropRepository: SeasonDropRepository,
    private val cacheRepository: CacheRepository,
    private val preferences: SharedPreferences,
    private val context: Context
) : ISeasonDropInteractor {

    override fun getSeasonDrop(): Single<SeasonDrop> {
        return seasonDropRepository.getSeasonDrop().map { drop ->
            val isCacheEnabled = preferences.getBoolean("cache_feature", false)
            if (isCacheEnabled && ConfigurationChecker.isNetworkAvailable(context)) {
                cacheRepository.saveDrop(drop)
            }
            drop
        }
    }
}
