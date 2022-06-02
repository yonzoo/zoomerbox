package com.zoomerbox.model.dto

import kotlinx.android.parcel.RawValue

/**
 *
 * DTO со всеми товарами текущего сезона
 *
 * @param seasonNumber номер сезона (идея - делать новый дроп через период времени и нумеровать дропы)
 * @param seasonBillboardImageUrl баннер для дропа
 * @param collections список коллекций (категорий) боксов в дропе
 */
data class SeasonDropDTO(
    val seasonNumber: Int,
    val seasonBillboardImageUrl: String,
    val collections: @RawValue List<CollectionDTO>
)
