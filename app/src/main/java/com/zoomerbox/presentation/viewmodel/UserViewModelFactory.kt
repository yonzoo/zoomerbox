package com.zoomerbox.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zoomerbox.data.repository.IUserRepository
import com.zoomerbox.presentation.view.util.ISchedulersProvider
import javax.inject.Inject

class UserViewModelFactory @Inject constructor(
    private val userRepository: IUserRepository,
    private val schedulersProvider: ISchedulersProvider
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(userRepository, schedulersProvider) as T
    }
}
