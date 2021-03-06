package com.zoomerbox.data.provider.remote.service

import com.zoomerbox.model.dto.UserDTO
import com.zoomerbox.model.util.EndpointUrls.BASE_URL
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApiService {

    @GET("${BASE_URL}/user")
    fun getUser(
        @Query("uid") uid: String,
        @Query("phone") phone: String
    ): Single<UserDTO>
}