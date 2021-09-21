package com.zoomerbox.model.app

import com.zoomerbox.model.dto.CollectionDTO
import com.zoomerbox.model.util.enumeration.ShopListItemTypeEnum

class Collection(val collectionName: String, val boxes: List<ZoomerBox>) : IShopListItem {

    override fun getType(): ShopListItemTypeEnum {
        return ShopListItemTypeEnum.COLLECTION
    }

    companion object {

        fun buildFromDTO(collectionDTO: CollectionDTO): Collection {
            return Collection(
                collectionDTO.collectionName,
                collectionDTO.boxes.map { boxDTO -> ZoomerBox.buildFromDTO(boxDTO) })
        }
    }
}
