package com.zoomerbox.model.item

import com.zoomerbox.model.enumeration.ShopListItemTypeEnum

class BannerItem(val imageUrl: String) : IShopListItem {

    override fun getType(): ShopListItemTypeEnum {
        return ShopListItemTypeEnum.BANNER
    }
}