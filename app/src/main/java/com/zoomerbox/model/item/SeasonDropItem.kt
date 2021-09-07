package com.zoomerbox.model.item

import com.zoomerbox.model.dto.SeasonDropDTO

class SeasonDropItem(
    val seasonNumber: Int,
    val seasonName: String,
    val bannerItem: BannerItem,
    val collections: List<CollectionItem>
) {

    companion object {

        fun buildFromDTO(seasonDropDTO: SeasonDropDTO): SeasonDropItem {
            return SeasonDropItem(
                seasonDropDTO.seasonNumber,
                seasonDropDTO.seasonName,
                BannerItem(seasonDropDTO.seasonBillboardImageUrl),
                seasonDropDTO.collections.map { collectionDTO ->
                    CollectionItem.buildFromDTO(
                        collectionDTO
                    )
                }
            )
        }
    }
}