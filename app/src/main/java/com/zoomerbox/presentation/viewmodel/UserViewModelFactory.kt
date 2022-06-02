package com.zoomerbox.presentation.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zoomerbox.data.repository.IUserRepository
import com.zoomerbox.data.repository.impl.CacheRepository
import com.zoomerbox.presentation.view.util.ISchedulersProvider
import javax.inject.Inject

/**
 * Фабрика для создания ViewModel для экрана с информацией о пользователе
 *
 * @param userRepository репозиторий с данными о пользователе
 * @param cacheRepository репозиторий с данными из кеша
 * @param schedulersProvider провайдер доступа к потокам
 * @param sharedPreferences объект с префами
 */
class UserViewModelFactory @Inject constructor(
    private val userRepository: IUserRepository,
    private val cacheRepository: CacheRepository,
    private val schedulersProvider: ISchedulersProvider,
    private val sharedPreferences: SharedPreferences
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(userRepository, cacheRepository, schedulersProvider, sharedPreferences) as T
    }
}
