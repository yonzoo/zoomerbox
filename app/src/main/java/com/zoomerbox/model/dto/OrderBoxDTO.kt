package com.zoomerbox.model.dto

/**
 *
 * DTO с данными по коробке в заказе
 *
 * @param box заказываемый бокс
 * @param count количество боксов для заказа
 */
data class OrderBoxDTO(
    val box: ZoomerBoxDTO,
    val count: Int
)
