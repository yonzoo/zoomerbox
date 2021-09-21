package com.zoomerbox.model.app

import com.zoomerbox.model.util.enumeration.ShopListItemTypeEnum

interface IShopListItem {

    fun getType(): ShopListItemTypeEnum
}
