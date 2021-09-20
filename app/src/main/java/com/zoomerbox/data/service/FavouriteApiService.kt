package com.zoomerbox.data.service

import com.zoomerbox.model.app.ZoomerBox
import com.zoomerbox.model.dto.ZoomerBoxDTO
import com.zoomerbox.model.util.EndpointUrls
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface FavouriteApiService {

    @GET("${EndpointUrls.BASE_URL}/favourite/all")
    fun getFavouriteBoxes(@Query("uid") uid: String): Single<List<ZoomerBoxDTO>>

    @GET("${EndpointUrls.BASE_URL}/favourite/flag")
    fun isBoxFavourite(
        @Query("uid") uid: String,
        @Query("boxName") boxName: String
    ): Single<Boolean>

    @POST("${EndpointUrls.BASE_URL}/favourite/toggle")
    fun toggleFavouriteBox(
        @Query("uid") uid: String,
        @Query("boxName") boxName: String
    ): Single<Boolean>

    @POST("${EndpointUrls.BASE_URL}/favourite/remove")
    fun removeBoxFromFavourite(
        @Query("uid") uid: String,
        @Query("boxName") boxName: String
    ): Single<List<ZoomerBox>>
}