package com.zoomerbox.data.provider.remote.service

import com.zoomerbox.model.dto.OrderBoxDTO
import com.zoomerbox.model.dto.OrderDTO
import com.zoomerbox.model.util.EndpointUrls
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface OrdersApiService {

    @GET("${EndpointUrls.BASE_URL}/orders/all")
    fun getOrders(@Query("uid") uid: String): Single<List<OrderDTO>>

    @POST("${EndpointUrls.BASE_URL}/orders/create")
    fun createOrder(
        @Query("uid") uid: String,
        @Query("cityName") cityName: String,
        @Query("fullName") fullName: String,
        @Query("postIndex") postIndex: String,
        @Body orderBoxDTOs: List<OrderBoxDTO>
    ): Single<OrderDTO>
}