package com.zoomerbox.data.repository.impl

import com.zoomerbox.data.repository.IShoppingCartRepository
import com.zoomerbox.data.provider.remote.service.ShoppingCartApiService
import com.zoomerbox.model.app.ShoppingCartItem
import io.reactivex.Single
import java.util.stream.Collectors
import javax.inject.Inject

class ShoppingCartRepository @Inject constructor(
    private val shoppingCartApiService: ShoppingCartApiService
) : IShoppingCartRepository {

    override fun getShoppingCartItems(uid: String): Single<List<ShoppingCartItem>> {
        return shoppingCartApiService.getShoppingCartItems(uid).map { cartItemsDTOs ->
            cartItemsDTOs.stream().map { cartItemDTO ->
                ShoppingCartItem.buildFromDTO(cartItemDTO)
            }.collect(Collectors.toList())
        }
    }

    override fun addShoppingCartItem(
        uid: String,
        shoppingCartItem: ShoppingCartItem
    ): Single<List<ShoppingCartItem>> {
        return shoppingCartApiService.addShoppingCartItem(uid, shoppingCartItem.toDTO())
            .map { cartItemsDTOs ->
                cartItemsDTOs.map { cartItemDTO -> ShoppingCartItem.buildFromDTO(cartItemDTO) }
            }
    }

    override fun reduceCountOfShoppingCartItems(
        uid: String,
        shoppingCartItem: ShoppingCartItem
    ): Single<List<ShoppingCartItem>> {
        return shoppingCartApiService.reduceCountOfShoppingCartItems(uid, shoppingCartItem.toDTO())
            .map { cartItemsDTOs ->
                cartItemsDTOs.map { cartItemDTO -> ShoppingCartItem.buildFromDTO(cartItemDTO) }
            }
    }

    override fun removeShoppingCartItem(
        uid: String,
        shoppingCartItem: ShoppingCartItem
    ): Single<List<ShoppingCartItem>> {
        return shoppingCartApiService.removeShoppingCartItem(uid, shoppingCartItem.toDTO())
            .map { cartItemsDTOs ->
                cartItemsDTOs.map { cartItemDTO -> ShoppingCartItem.buildFromDTO(cartItemDTO) }
            }
    }

    override fun removeShoppingCartItems(
        uid: String,
        shoppingCartItems: List<ShoppingCartItem>
    ): Single<List<ShoppingCartItem>> {
        return shoppingCartApiService.removeShoppingCartItems(
            uid,
            shoppingCartItems.map { item -> item.toDTO() })
            .map { cartItemsDTOs ->
                cartItemsDTOs.map { cartItemDTO -> ShoppingCartItem.buildFromDTO(cartItemDTO) }
            }
    }

    override fun toggleSelectShoppingCartItem(
        uid: String,
        shoppingCartItem: ShoppingCartItem
    ): Single<List<ShoppingCartItem>> {
        return shoppingCartApiService.toggleSelectShoppingCartItem(
            uid,
            shoppingCartItem.toDTO()
        ).map { cartItemsDTOs ->
            cartItemsDTOs.map { cartItemDTO -> ShoppingCartItem.buildFromDTO(cartItemDTO) }
        }
    }

    override fun selectAllShoppingCartItems(
        uid: String,
        shoppingCartItems: List<ShoppingCartItem>
    ): Single<List<ShoppingCartItem>> {
        return shoppingCartApiService.selectAllShoppingCartItems(
            uid,
            shoppingCartItems.map { item -> item.toDTO() }
        ).map { cartItemsDTOs ->
            cartItemsDTOs.map { cartItemDTO -> ShoppingCartItem.buildFromDTO(cartItemDTO) }
        }
    }

    override fun deselectAllShoppingCartItems(
        uid: String,
        shoppingCartItems: List<ShoppingCartItem>
    ): Single<List<ShoppingCartItem>> {
        return shoppingCartApiService.deselectAllShoppingCartItems(
            uid,
            shoppingCartItems.map { item -> item.toDTO() }
        ).map { cartItemsDTOs ->
            cartItemsDTOs.map { cartItemDTO -> ShoppingCartItem.buildFromDTO(cartItemDTO) }
        }
    }


    override fun getImplName(): String {
        return this::class.java.simpleName
    }
}