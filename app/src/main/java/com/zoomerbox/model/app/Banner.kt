package com.zoomerbox.model.app

import com.zoomerbox.model.enumeration.ShopListItemTypeEnum

class Banner(val imageUrl: String) : IShopListItem {

    override fun getType(): ShopListItemTypeEnum {
        return ShopListItemTypeEnum.BANNER
    }
}
