package com.zoomerbox.data.provider.remote.service

import com.zoomerbox.model.dto.TokenDTO
import com.zoomerbox.model.dto.UserDTO
import com.zoomerbox.model.util.EndpointUrls.AUTHENTICATE_BASE_URL
import com.zoomerbox.model.util.EndpointUrls.BASE_URL
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserApiService {

    @GET("${BASE_URL}/user")
    fun getUser(): Single<UserDTO>

    @POST("${AUTHENTICATE_BASE_URL}/authenticate")
    fun getToken(
        @Header("X-Auth-Token") token: String,
        @Header("X-Phone-Number") phoneNumber: String
    ) : Single<TokenDTO>
}