package com.zoomerbox.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zoomerbox.data.repository.impl.MockSeasonDropRepository
import com.zoomerbox.presentation.view.util.SchedulersProvider
import javax.inject.Inject

class ShopViewModelFactory @Inject constructor() : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        //TODO replace with di implementation and real repository
        return ShopViewModel(MockSeasonDropRepository(), SchedulersProvider()) as T
    }
}
