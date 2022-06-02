package com.zoomerbox.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zoomerbox.domain.ISeasonDropInteractor
import com.zoomerbox.presentation.view.util.ISchedulersProvider
import javax.inject.Inject

/**
 * Фабрика для создания ViewModel для экрана с товарами
 *
 * @param seasonDropInteractor интерактор для получения и обработки товаров
 * @param schedulersProvider провайдер доступа к потокам
 */
class ShopViewModelFactory @Inject constructor(
    private val seasonDropInteractor: ISeasonDropInteractor,
    private val schedulersProvider: ISchedulersProvider
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ShopViewModel(seasonDropInteractor, schedulersProvider) as T
    }
}
