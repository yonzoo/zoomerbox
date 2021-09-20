package com.zoomerbox.data.service

import com.zoomerbox.model.dto.ShoppingCartItemDTO
import com.zoomerbox.model.util.EndpointUrls
import io.reactivex.Single
import retrofit2.http.*

interface ShoppingCartApiService {

    @GET("${EndpointUrls.BASE_URL}/cart/items")
    fun getShoppingCartItems(@Query("uid") uid: String): Single<List<ShoppingCartItemDTO>>

    @POST("${EndpointUrls.BASE_URL}/cart/add")
    fun addShoppingCartItem(
        @Query("uid") uid: String,
        @Body shoppingCartItemDTO: ShoppingCartItemDTO
    ): Single<List<ShoppingCartItemDTO>>

    @PATCH("${EndpointUrls.BASE_URL}/cart/reduceCount")
    fun reduceCountOfShoppingCartItems(
        @Query("uid") uid: String,
        @Body shoppingCartItemDTO: ShoppingCartItemDTO
    ): Single<List<ShoppingCartItemDTO>>

    @POST("${EndpointUrls.BASE_URL}/cart/remove")
    fun removeShoppingCartItem(
        @Query("uid") uid: String,
        @Body shoppingCartItemDTO: ShoppingCartItemDTO
    ): Single<List<ShoppingCartItemDTO>>

    @POST("${EndpointUrls.BASE_URL}/cart/removeAll")
    fun removeShoppingCartItems(
        @Query("uid") uid: String,
        @Body shoppingCartItemDTOs: List<ShoppingCartItemDTO>
    ): Single<List<ShoppingCartItemDTO>>

    @POST("${EndpointUrls.BASE_URL}/cart/toggleSelect")
    fun toggleSelectShoppingCartItem(
        @Query("uid") uid: String,
        @Body shoppingCartItemDTO: ShoppingCartItemDTO
    ): Single<List<ShoppingCartItemDTO>>

    @POST("${EndpointUrls.BASE_URL}/cart/selectAll")
    fun selectAllShoppingCartItems(
        @Query("uid") uid: String,
        @Body shoppingCartItemDTOs: List<ShoppingCartItemDTO>
    ): Single<List<ShoppingCartItemDTO>>

    @POST("${EndpointUrls.BASE_URL}/cart/deselectAll")
    fun deselectAllShoppingCartItems(
        @Query("uid") uid: String,
        @Body shoppingCartItemDTOs: List<ShoppingCartItemDTO>
    ): Single<List<ShoppingCartItemDTO>>
}