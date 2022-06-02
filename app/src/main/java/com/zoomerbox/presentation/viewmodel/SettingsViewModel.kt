package com.zoomerbox.presentation.viewmodel

import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zoomerbox.data.repository.ICacheRepository
import com.zoomerbox.presentation.view.util.ISchedulersProvider
import io.reactivex.disposables.Disposable

/**
 *
 * ViewModel для экрана настроек приложения.
 *
 * @param cacheRepository репозиторий с данными из кеша
 * @param schedulersProvider провайдер шедулеров
 */
class SettingsViewModel(
    @NonNull private val cacheRepository: ICacheRepository,
    @NonNull private val schedulersProvider: ISchedulersProvider
) : ViewModel() {

    private val resultLiveData = MutableLiveData<Boolean>()
    private var disposable: Disposable? = null

    /**
     * Метод для очистки кэша.
     */
    fun clearCache() {
        disposable = cacheRepository.clearCache()
            .doOnSubscribe {
                resultLiveData.postValue(false)
            }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .subscribe({ result ->
                if (result) {
                    resultLiveData.postValue(true)
                }
            }, {
                resultLiveData.postValue(false)
            })
    }

    /**
     * @return LiveData со флагом, был ли успешно очищен кэш.
     */
    fun getResultLiveData(): LiveData<Boolean> {
        return resultLiveData
    }

    override fun onCleared() {
        super.onCleared()
        if (disposable != null && !disposable!!.isDisposed) {
            disposable!!.dispose()
            disposable = null
        }
        Log.d(TAG, "onCleared() called")
    }

    companion object {
        val TAG: String = this::class.java.declaringClass.simpleName
    }
}