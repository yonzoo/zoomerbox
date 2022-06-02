package com.zoomerbox.model.app

import com.zoomerbox.model.dto.SeasonDropDTO
import com.zoomerbox.model.entity.SeasonDropEntity

/**
 *
 * Модель со всеми товарами текущего сезона
 *
 * @param seasonNumber номер сезона (идея - делать новый дроп через период времени и нумеровать дропы)
 * @param banner баннер для дропа
 * @param collections список коллекций (категорий) боксов в дропе
 */
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
