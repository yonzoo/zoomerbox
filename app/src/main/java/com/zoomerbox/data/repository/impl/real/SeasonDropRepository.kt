package com.zoomerbox.data.repository.impl.real

import com.zoomerbox.data.repository.ISeasonDropRepository
import com.zoomerbox.data.service.SeasonDropApiService
import com.zoomerbox.model.app.SeasonDrop
import io.reactivex.Single
import javax.inject.Inject

class SeasonDropRepository @Inject constructor(private val apiService: SeasonDropApiService) :
    ISeasonDropRepository {

    override fun getSeasonDrop(): Single<SeasonDrop> {
        return apiService.getSeasonDrop().map { dropDTO -> SeasonDrop.buildFromDTO(dropDTO) }
    }

    override fun getImplName(): String {
        return this::class.java.simpleName
    }
}