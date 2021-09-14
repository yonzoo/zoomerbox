package com.zoomerbox.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zoomerbox.data.repository.impl.mock.MockSeasonDropRepository
import com.zoomerbox.data.repository.impl.mock.MockShoppingCartRepository
import com.zoomerbox.presentation.view.util.SchedulersProvider
import javax.inject.Inject

class ShoppingCartViewModelFactory @Inject constructor() : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ShoppingCartViewModel(MockShoppingCartRepository(), SchedulersProvider()) as T
    }
}