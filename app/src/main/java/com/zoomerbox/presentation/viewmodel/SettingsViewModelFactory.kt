package com.zoomerbox.presentation.viewmodel

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zoomerbox.data.repository.ICacheRepository
import com.zoomerbox.presentation.view.util.ISchedulersProvider
import javax.inject.Inject


class SettingsViewModelFactory @Inject constructor(
    @NonNull private val cacheRepository: ICacheRepository,
    @NonNull private val schedulersProvider: ISchedulersProvider
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SettingsViewModel(cacheRepository, schedulersProvider) as T
    }
}