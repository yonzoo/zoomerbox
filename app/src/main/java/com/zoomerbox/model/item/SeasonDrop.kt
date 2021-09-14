package com.zoomerbox.model.item

import com.zoomerbox.model.dto.SeasonDropDTO

class SeasonDrop(
    val seasonNumber: Int,
    val seasonName: String,
    val banner: Banner,
    val collections: List<Collection>
) {

    companion object {

        fun buildFromDTO(seasonDropDTO: SeasonDropDTO): SeasonDrop {
            return SeasonDrop(
                seasonDropDTO.seasonNumber,
                seasonDropDTO.seasonName,
                Banner(seasonDropDTO.seasonBillboardImageUrl),
                seasonDropDTO.collections.map { collectionDTO ->
                    Collection.buildFromDTO(
                        collectionDTO
                    )
                }
            )
        }
    }
}
