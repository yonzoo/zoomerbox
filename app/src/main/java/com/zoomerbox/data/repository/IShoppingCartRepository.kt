package com.zoomerbox.data.repository

import com.zoomerbox.data.exception.RequestFailedException
import com.zoomerbox.model.item.ShoppingCartItem

interface IShoppingCartRepository {

    @Throws(RequestFailedException::class)
    fun getShoppingCartItems(): List<ShoppingCartItem>

    fun getImplName(): String
}