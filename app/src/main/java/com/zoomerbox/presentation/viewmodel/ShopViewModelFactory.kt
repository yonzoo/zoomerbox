package com.zoomerbox.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zoomerbox.data.repository.ISeasonDropRepository
import com.zoomerbox.presentation.view.util.ISchedulersProvider
import com.zoomerbox.presentation.view.util.SchedulersProvider
import javax.inject.Inject

class ShopViewModelFactory @Inject constructor(
    private val seasonDropRepository: ISeasonDropRepository,
    private val schedulersProvider: ISchedulersProvider
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ShopViewModel(seasonDropRepository, schedulersProvider) as T
    }
}
