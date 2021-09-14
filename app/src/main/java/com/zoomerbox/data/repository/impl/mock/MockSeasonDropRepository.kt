package com.zoomerbox.data.repository.impl.mock

import com.zoomerbox.data.repository.ISeasonDropRepository
import com.zoomerbox.data.repository.impl.mock.utils.MockDataProvider.getCollections
import com.zoomerbox.model.dto.SeasonDropDTO
import com.zoomerbox.model.item.SeasonDrop

class MockSeasonDropRepository : ISeasonDropRepository {

    override fun getSeasonDrop(): SeasonDrop {
        return SeasonDrop.buildFromDTO(
            SeasonDropDTO(
                0,
                "Моксезон",
                "https://cdn.vox-cdn.com/thumbor/BcRyrvD1-ym1dzBgoer3GudBb8Q=/0x0:848x926/1200x800/filters:focal(357x533:491x667)/cdn.vox-cdn.com/uploads/chorus_image/image/69764737/E44h7SmUYAAOGxd.0.jpg",
                getCollections()
            )
        )
    }

    override fun getImplName(): String {
        return this::class.java.simpleName
    }
}
