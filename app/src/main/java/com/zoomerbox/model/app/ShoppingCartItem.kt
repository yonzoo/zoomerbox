package com.zoomerbox.model.app

import com.zoomerbox.model.dto.ShoppingCartItemDTO

/**
 *
 * Модель с данными предмета в корзине
 *
 * @param box данные по коробке
 * @param selected выбрана ли коробка
 * @param count количество заказываемых коробок
 * @param isFavourite флаг, есть ли бокс в избранных
 */
class ShoppingCartItem(
    val box: ZoomerBox,
    val selected: Boolean,
    val count: Int,
    var isFavourite: Boolean
) {

    fun toDTO(): ShoppingCartItemDTO {
        return ShoppingCartItemDTO(
            box.toDTO(),
            selected,
            count,
            isFavourite
        )
    }

    companion object {

        fun buildFromDTO(shoppingCartItemDTO: ShoppingCartItemDTO): ShoppingCartItem {
            return ShoppingCartItem(
                ZoomerBox.buildFromDTO(shoppingCartItemDTO.box),
                shoppingCartItemDTO.selected,
                shoppingCartItemDTO.count,
                shoppingCartItemDTO.favourite
            )
        }
    }
}
