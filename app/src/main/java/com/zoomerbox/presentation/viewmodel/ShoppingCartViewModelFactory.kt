package com.zoomerbox.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zoomerbox.data.repository.IFavouriteRepository
import com.zoomerbox.data.repository.IShoppingCartRepository
import com.zoomerbox.presentation.view.util.ISchedulersProvider
import javax.inject.Inject

/**
 * Фабрика для создания ViewModel для экрана с настройками
 *
 * @param shoppingCartRepository репозиторий данных для корзины
 * @param favouriteRepository репозиторий с избранными боксами
 * @param schedulersProvider провайдер доступа к потокам
 */
class ShoppingCartViewModelFactory @Inject constructor(
    private val shoppingCartRepository: IShoppingCartRepository,
    private val favouriteRepository: IFavouriteRepository,
    private val schedulersProvider: ISchedulersProvider
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ShoppingCartViewModel(
            shoppingCartRepository,
            favouriteRepository,
            schedulersProvider
        ) as T
    }
}
