package com.zoomerbox.model.app

import com.zoomerbox.model.util.enumeration.ShopListItemTypeEnum

/**
 *
 * Интерфейс для элемента списка на экране товаров
 */
interface IShopListItem {

    fun getType(): ShopListItemTypeEnum
}
