package com.zoomerbox.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

/**
 * Фабрика для создания ViewModel для экрана входа в приложение
 *
 * @param auth объект, выполняющий аутентификацию пользователя через Firebase
 */
class SignInViewModelFactory @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SignInViewModel(auth) as T
    }
}
