package com.zoomerbox.data.provider.remote.service

import com.zoomerbox.model.dto.SeasonDropDTO
import com.zoomerbox.model.util.EndpointUrls.BASE_URL
import io.reactivex.Single
import retrofit2.http.GET

interface SeasonDropApiService {

    @GET("${BASE_URL}/drop/season")
    fun getSeasonDrop(): Single<SeasonDropDTO>
}