package com.zoomerbox.model.app

import com.zoomerbox.model.dto.ShoppingCartItemDTO

class ShoppingCartItem(
    val box: ZoomerBox,
    val selected: Boolean,
    val count: Int,
    val isFavourite: Boolean
) {

    companion object {

        fun buildFromDTO(shoppingCartItemDTO: ShoppingCartItemDTO): ShoppingCartItem {
            return ShoppingCartItem(
                ZoomerBox.buildFromDTO(shoppingCartItemDTO.box),
                shoppingCartItemDTO.selected,
                shoppingCartItemDTO.count,
                shoppingCartItemDTO.isFavourite
            )
        }
    }
}
