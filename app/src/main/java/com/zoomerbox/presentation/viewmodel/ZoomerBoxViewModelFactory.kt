package com.zoomerbox.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zoomerbox.data.repository.IFavouriteRepository
import com.zoomerbox.data.repository.IShoppingCartRepository
import com.zoomerbox.presentation.view.util.ISchedulersProvider
import javax.inject.Inject

/**
 * Фабрика для создания ViewModel для экрана с деталями бокса
 *
 * @param userRepository репозиторий с данными о пользователе
 * @param cacheRepository репозиторий с данными из кеша
 * @param schedulersProvider провайдер доступа к потокам
 * @param sharedPreferences объект с префами
 */
class ZoomerBoxViewModelFactory @Inject constructor(
    private val shoppingCartRepository: IShoppingCartRepository,
    private val favouriteRepository: IFavouriteRepository,
    private val schedulersProvider: ISchedulersProvider
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ZoomerBoxViewModel(
            shoppingCartRepository,
            favouriteRepository,
            schedulersProvider
        ) as T
    }
}
