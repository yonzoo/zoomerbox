package com.zoomerbox.model.app

import com.zoomerbox.model.util.enumeration.ShopListItemTypeEnum

class Banner(val imageUrl: String) : IShopListItem {

    override fun getType(): ShopListItemTypeEnum {
        return ShopListItemTypeEnum.BANNER
    }
}
