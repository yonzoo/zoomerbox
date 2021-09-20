package com.zoomerbox.data.store.impl

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zoomerbox.data.store.ICartItemsStore
import com.zoomerbox.model.app.ShoppingCartItem
import com.zoomerbox.presentation.view.util.BundleKeys
import javax.inject.Inject

class CartItemsStorePrefsImpl @Inject constructor(
    private val preferences: SharedPreferences
) : ICartItemsStore {

    override fun saveCartItems(cartItems: List<ShoppingCartItem>) {
        if (cartItems.isEmpty())
            return
        preferences
            .edit()
            .putString(BundleKeys.CART_ITEMS, Gson().toJson(cartItems))
            .apply()
    }

    override fun getCartItems(): List<ShoppingCartItem> {
        val cartItemsStr = preferences.getString(BundleKeys.CART_ITEMS, null)
        val cartItemsListType = object : TypeToken<List<ShoppingCartItem>>() {}.type
        if (cartItemsStr == null) return emptyList()
        return Gson().fromJson(cartItemsStr, cartItemsListType)
    }

    override fun clearCartItems(): Boolean {
        preferences
            .edit()
            .remove(BundleKeys.CART_ITEMS)
            .apply()
        return true
    }
}
