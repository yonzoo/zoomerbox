package com.zoomerbox.presentation.viewmodel

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zoomerbox.domain.ISeasonDropInteractor
import com.zoomerbox.model.app.SeasonDrop
import com.zoomerbox.presentation.view.util.ISchedulersProvider
import io.reactivex.disposables.Disposable

/**
 *
 * ViewModel для экрана c товарами.
 *
 * @param interactor интерактор для получения и обработки товаров
 * @param schedulersProvider провайдер доступа к потокам
 */
class ShopViewModel(
    @NonNull private val interactor: ISeasonDropInteractor,
    @NonNull private val schedulersProvider: ISchedulersProvider
) : ViewModel() {

    private val progressLiveData = MutableLiveData<Boolean>()
    private val errorLiveData = MutableLiveData<Throwable>()
    private val seasonDropLiveData = MutableLiveData<SeasonDrop>()
    var disposable: Disposable? = null

    fun loadSeasonDrop() {
        disposable = interactor.getSeasonDrop()
            .doOnSubscribe { progressLiveData.postValue(true) }
            .doOnTerminate { progressLiveData.postValue(false) }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .subscribe({ drop ->
                seasonDropLiveData.postValue(drop)
            }, { ex ->
                errorLiveData.postValue(ex)
            })
    }

    fun clearViewModel() {
        onCleared()
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
        disposable = null
    }

    fun getProgressLiveData(): LiveData<Boolean> {
        return progressLiveData
    }

    fun getErrorLiveData(): LiveData<Throwable> {
        return errorLiveData
    }

    fun getSeasonDropLiveData(): LiveData<SeasonDrop> {
        return seasonDropLiveData
    }

    companion object {
        val TAG: String = this::class.java.declaringClass.simpleName
    }
}
