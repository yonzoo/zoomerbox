package com.zoomerbox.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zoomerbox.data.repository.IFavouriteRepository
import com.zoomerbox.presentation.view.util.ISchedulersProvider
import javax.inject.Inject

class FavouriteViewModelFactory @Inject constructor(
    private val favouriteRepository: IFavouriteRepository,
    private val schedulersProvider: ISchedulersProvider
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavouriteViewModel(favouriteRepository, schedulersProvider) as T
    }
}
