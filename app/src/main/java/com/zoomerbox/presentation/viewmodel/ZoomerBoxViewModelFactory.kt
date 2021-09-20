package com.zoomerbox.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zoomerbox.data.repository.IFavouriteRepository
import com.zoomerbox.data.repository.IShoppingCartRepository
import com.zoomerbox.presentation.view.util.ISchedulersProvider
import javax.inject.Inject

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
