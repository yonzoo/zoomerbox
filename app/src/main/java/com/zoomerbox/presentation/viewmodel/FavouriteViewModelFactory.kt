package com.zoomerbox.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zoomerbox.data.repository.impl.mock.MockFavouriteRepository
import com.zoomerbox.presentation.view.util.SchedulersProvider
import javax.inject.Inject

class FavouriteViewModelFactory @Inject constructor() : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavouriteViewModel(MockFavouriteRepository(), SchedulersProvider()) as T
    }
}
