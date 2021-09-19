package com.zoomerbox.data.store

import com.zoomerbox.model.app.ShoppingCartItem

interface ICartItemsStore {

    fun saveCartItems(cartItems: List<ShoppingCartItem>)
    fun getCartItems(): List<ShoppingCartItem>
    fun clearCartItems(): Boolean
}
