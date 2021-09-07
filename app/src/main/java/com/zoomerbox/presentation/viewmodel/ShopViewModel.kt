package com.zoomerbox.presentation.viewmodel

import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zoomerbox.presentation.view.util.ISchedulersProvider
import com.zoomerbox.data.repository.ISeasonDropRepository
import com.zoomerbox.model.dto.SeasonDropDTO
import com.zoomerbox.model.item.SeasonDropItem
import io.reactivex.Single
import io.reactivex.disposables.Disposable

class ShopViewModel(
    @NonNull private val repository: ISeasonDropRepository,
    @NonNull private val schedulersProvider: ISchedulersProvider
) : ViewModel() {

    private val progressLiveData = MutableLiveData<Boolean>()
    private val errorLiveData = MutableLiveData<Throwable>()
    private val seasonDropLiveData = MutableLiveData<SeasonDropItem>()
    private var disposable: Disposable? = null

    fun loadSeasonDrop() {
        disposable = Single.fromCallable { repository.getSeasonDrop() }
            .doOnSubscribe { progressLiveData.postValue(true) }
            .doOnTerminate { progressLiveData.postValue(false) }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .subscribe({ drop ->
                Log.d(
                    TAG,
                    "${repository.getImplName()} successfully returned the season drop body: $drop"
                )
                seasonDropLiveData.postValue(drop)
            }, { ex ->
                Log.e(
                    TAG,
                    "${repository.getImplName()} failed to return the season drop body with the exception: ${ex.message}"
                )
                errorLiveData.postValue(ex)
            })
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

    fun getSeasonDropLiveData(): LiveData<SeasonDropItem> {
        return seasonDropLiveData
    }

    companion object {
        val TAG: String = this::class.java.declaringClass.simpleName
    }
}
