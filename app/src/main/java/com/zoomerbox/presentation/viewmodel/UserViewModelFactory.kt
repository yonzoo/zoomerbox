package com.zoomerbox.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zoomerbox.data.repository.impl.mock.MockUserRepository
import com.zoomerbox.presentation.view.util.SchedulersProvider
import javax.inject.Inject

class UserViewModelFactory @Inject constructor() : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(MockUserRepository(), SchedulersProvider()) as T
    }
}
