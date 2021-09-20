package com.zoomerbox.presentation.viewmodel

import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.zoomerbox.data.repository.IFavouriteRepository
import com.zoomerbox.model.app.ZoomerBox
import com.zoomerbox.presentation.view.util.ISchedulersProvider
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
        val authUser = FirebaseAuth.getInstance().currentUser
        disposable = repository.getFavouriteItems(authUser!!.uid)
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

    fun removeItemFromFavourite(zoomerBox: ZoomerBox) {
        val authUser = FirebaseAuth.getInstance().currentUser
        disposable = repository.removeBoxFromFavourite(authUser!!.uid, zoomerBox.name)
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