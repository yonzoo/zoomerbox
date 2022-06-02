package com.zoomerbox.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zoomerbox.domain.IUserInteractor
import com.zoomerbox.presentation.view.util.ISchedulersProvider
import javax.inject.Inject

/**
 * Фабрика для создания ViewModel для базового экрана аутентификации
 *
 * @param userInteractor интерактор для обработки данных пользователя
 * @param schedulersProvider провайдер доступа к потокам
 */
class DefaultViewModelFactory @Inject constructor(
    private val userInteractor: IUserInteractor,
    private val schedulersProvider: ISchedulersProvider
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DefaultViewModel(userInteractor, schedulersProvider) as T
    }
}
