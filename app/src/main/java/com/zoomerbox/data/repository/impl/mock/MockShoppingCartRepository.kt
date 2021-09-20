package com.zoomerbox.data.repository.impl.mock

import com.zoomerbox.data.repository.IShoppingCartRepository
import com.zoomerbox.data.repository.impl.mock.utils.MockDataProvider
import com.zoomerbox.model.dto.ShoppingCartItemDTO
import com.zoomerbox.model.app.ShoppingCartItem
import io.reactivex.Single

//class MockShoppingCartRepository : IShoppingCartRepository {
//
//    override fun getShoppingCartItems(): List<ShoppingCartItem> {
//        return MockDataProvider.getBoxes(
//            listOf(
//                "X_MARVEL",
//                "X_DC",
//                "X_BUBBLECOMICS",
//                "X_LUCASFILM"
//            )
//        ).map { box ->
//            ShoppingCartItem.buildFromDTO(
//                ShoppingCartItemDTO(box, true, 2, false)
//            )
//        }
//    }
//
//    override fun getShoppingCartItems(uid: String): Single<List<ShoppingCartItem>> {
//        TODO("Not yet implemented")
//    }
//
//    override fun getImplName(): String {
//        return this::class.java.simpleName
//    }
//}