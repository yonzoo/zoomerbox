package com.zoomerbox.presentation.viewmodel

import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zoomerbox.data.repository.IFavouriteRepository
import com.zoomerbox.model.app.ZoomerBox
import com.zoomerbox.presentation.view.util.ISchedulersProvider
import io.reactivex.Single
import io.reactivex.disposables.Disposable

class FavouriteViewModel(
    @NonNull private val repository: IFavouriteRepository,
    @NonNull private val schedulersProvider: ISchedulersProvider
) : ViewModel() {

    private val progressLiveData = MutableLiveData<Boolean>()
    private val errorLiveData = MutableLiveData<Throwable>()
    private val favouriteItemsLiveData = MutableLiveData<List<ZoomerBox>>()
    private var disposable: Disposable? = null

    fun loadFavouriteItems() {
        disposable = Single.fromCallable { repository.getFavouriteItems() }
            .doOnSubscribe { progressLiveData.postValue(true) }
            .doOnTerminate { progressLiveData.postValue(false) }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .subscribe({ favouriteItems ->
                Log.d(
                    TAG,
                    "${repository.getImplName()} successfully returned the favourite items: $favouriteItems"
                )
                favouriteItemsLiveData.postValue(favouriteItems)
            }, { ex ->
                Log.e(
                    TAG,
                    "${repository.getImplName()} failed to return the favourite items with the exception: ${ex.message}"
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

    fun getFavouriteItemsLiveData(): LiveData<List<ZoomerBox>> {
        return favouriteItemsLiveData
    }

    companion object {
        val TAG: String = this::class.java.declaringClass.simpleName
    }
}