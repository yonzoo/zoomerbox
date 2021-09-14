package com.zoomerbox.model.dto

data class ShoppingCartItemDTO(
    val box: ZoomerBoxDTO,
    val selected: Boolean,
    val count: Int,
    val isFavourite: Boolean
)
