package com.zoomerbox.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zoomerbox.data.repository.IOrdersRepository
import com.zoomerbox.presentation.view.util.ISchedulersProvider
import javax.inject.Inject

/**
 * Фабрика для создания ViewModel для экрана с заказами
 *
 * @param ordersRepository репозиторий данных по заказам
 * @param schedulersProvider провайдер доступа к потокам
 */
class OrdersViewModelFactory @Inject constructor(
    private val ordersRepository: IOrdersRepository,
    private val schedulersProvider: ISchedulersProvider
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OrdersViewModel(ordersRepository, schedulersProvider) as T
    }
}
