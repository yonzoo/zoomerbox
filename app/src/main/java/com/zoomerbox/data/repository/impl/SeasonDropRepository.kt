package com.zoomerbox.data.repository.impl

import android.content.Context
import com.zoomerbox.data.provider.remote.service.SeasonDropApiService
import com.zoomerbox.data.repository.ISeasonDropRepository
import com.zoomerbox.domain.util.ConfigurationChecker
import com.zoomerbox.model.app.SeasonDrop
import io.reactivex.Single
import javax.inject.Inject

class SeasonDropRepository @Inject constructor(
    private val apiService: SeasonDropApiService,
    private val cacheRepository: CacheRepository,
    private val context: Context
) :
    ISeasonDropRepository {

    override fun getSeasonDrop(): Single<SeasonDrop> {
        if (ConfigurationChecker.isNetworkAvailable(context) || !cacheRepository.isCacheEnabled()) {
            return apiService.getSeasonDrop()
                .map { dropDTO -> SeasonDrop.buildFromDTO(dropDTO) }
        } else {
            return cacheRepository.getSeasonDrop()
        }
    }

    override fun getImplName(): String {
        return this::class.java.simpleName
    }
}