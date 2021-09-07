package com.zoomerbox.model.item

import com.zoomerbox.model.enumeration.ShopListItemTypeEnum

interface IShopListItem {

    fun getType(): ShopListItemTypeEnum
}
