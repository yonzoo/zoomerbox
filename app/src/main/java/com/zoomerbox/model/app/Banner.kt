package com.zoomerbox.model.app

import com.zoomerbox.model.util.enumeration.ShopListItemTypeEnum

/**
 *
 * Модель с деталями баннера на странице товаров
 *
 * @param imageUrl url картинки для баннера
 */
class Banner(val imageUrl: String) : IShopListItem {

    override fun getType(): ShopListItemTypeEnum {
        return ShopListItemTypeEnum.BANNER
    }
}
