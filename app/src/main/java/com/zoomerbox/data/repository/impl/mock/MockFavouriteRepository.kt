package com.zoomerbox.data.repository.impl.mock

import com.zoomerbox.data.repository.IFavouriteRepository
import com.zoomerbox.data.repository.impl.mock.utils.MockDataProvider
import com.zoomerbox.model.item.ZoomerBox

class MockFavouriteRepository : IFavouriteRepository {
    
    override fun getFavouriteItems(): List<ZoomerBox> {
        return MockDataProvider.getBoxes(
            listOf(
                "X_MARVEL",
                "X_DC",
                "X_BUBBLECOMICS",
                "X_LUCASFILM",
                "X_KEK",
                "X_LOL"
            )
        ).map { zoomerBoxDTO -> ZoomerBox.buildFromDTO(zoomerBoxDTO) }
    }

    override fun getImplName(): String {
        return this::class.java.simpleName
    }
}