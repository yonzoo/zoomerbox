package com.zoomerbox.model.dto

/**
 *
 * DTO с данными предмета в корзине
 *
 * @param box данные по коробке
 * @param selected выбрана ли коробка
 * @param count количество заказываемых коробок
 * @param favourite флаг, есть ли бокс в избранных
 */
data class ShoppingCartItemDTO(
    val box: ZoomerBoxDTO,
    val selected: Boolean,
    val count: Int,
    val favourite: Boolean
)
