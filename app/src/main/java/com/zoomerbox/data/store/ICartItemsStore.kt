package com.zoomerbox.data.store

import com.zoomerbox.model.item.ShoppingCartItem

interface ICartItemsStore {

    fun saveCartItems(cartItems: List<ShoppingCartItem>)
    fun getCartItems(): List<ShoppingCartItem>
    fun clearCartItems(): Boolean
}
