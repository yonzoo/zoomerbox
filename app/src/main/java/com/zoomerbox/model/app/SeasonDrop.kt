package com.zoomerbox.model.app

import com.zoomerbox.model.dto.SeasonDropDTO

class SeasonDrop(
    val seasonNumber: Int,
    val banner: Banner,
    val collections: List<Collection>
) {

    companion object {

        fun buildFromDTO(seasonDropDTO: SeasonDropDTO): SeasonDrop {
            return SeasonDrop(
                seasonDropDTO.seasonNumber,
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
