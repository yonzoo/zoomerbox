package com.zoomerbox.presentation.view.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.zoomerbox.model.item.ShoppingCartItem

class CartItemsListDiffUtilCallback(
    private val oldCartItems: List<ShoppingCartItem>,
    private val newCartItems: List<ShoppingCartItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldCartItems.size
    }

    override fun getNewListSize(): Int {
        return newCartItems.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCartItems[oldItemPosition].box == newCartItems[newItemPosition].box
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCartItems[oldItemPosition].isFavourite == newCartItems[newItemPosition].isFavourite
                && oldCartItems[oldItemPosition].selected == newCartItems[newItemPosition].selected
                && oldCartItems[oldItemPosition].count == newCartItems[newItemPosition].count
    }
}
