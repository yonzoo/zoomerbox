package com.zoomerbox.model.item

import com.zoomerbox.model.dto.CollectionDTO
import com.zoomerbox.model.dto.ZoomerBoxDTO
import com.zoomerbox.model.enumeration.ShopListItemTypeEnum

class CollectionItem(val collectionName: String, val boxes: List<ZoomerBoxItem>) : IShopListItem {

    override fun getType(): ShopListItemTypeEnum {
        return ShopListItemTypeEnum.COLLECTION
    }

    companion object {

        fun buildFromDTO(collectionDTO: CollectionDTO): CollectionItem {
            return CollectionItem(
                collectionDTO.collectionName,
                collectionDTO.boxes.map { boxDTO -> ZoomerBoxItem.buildFromDTO(boxDTO) })
        }
    }
}