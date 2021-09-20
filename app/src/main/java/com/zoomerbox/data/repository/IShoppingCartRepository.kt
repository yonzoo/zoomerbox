package com.zoomerbox.data.repository

import com.zoomerbox.data.exception.RequestFailedException
import com.zoomerbox.model.app.ShoppingCartItem
import io.reactivex.Single

interface IShoppingCartRepository {

    @Throws(RequestFailedException::class)
    fun getShoppingCartItems(uid: String): Single<List<ShoppingCartItem>>

    fun addShoppingCartItem(
        uid: String,
        shoppingCartItem: ShoppingCartItem
    ): Single<List<ShoppingCartItem>>

    fun reduceCountOfShoppingCartItems(
        uid: String,
        shoppingCartItem: ShoppingCartItem
    ): Single<List<ShoppingCartItem>>

    fun removeShoppingCartItem(
        uid: String,
        shoppingCartItem: ShoppingCartItem
    ): Single<List<ShoppingCartItem>>

    fun removeShoppingCartItems(
        uid: String,
        shoppingCartItems: List<ShoppingCartItem>
    ): Single<List<ShoppingCartItem>>

    fun toggleSelectShoppingCartItem(
        uid: String,
        shoppingCartItem: ShoppingCartItem
    ): Single<List<ShoppingCartItem>>

    fun selectAllShoppingCartItems(
        uid: String,
        shoppingCartItems: List<ShoppingCartItem>
    ): Single<List<ShoppingCartItem>>

    fun deselectAllShoppingCartItems(
        uid: String,
        shoppingCartItems: List<ShoppingCartItem>
    ): Single<List<ShoppingCartItem>>

    fun getImplName(): String
}