package com.zoomerbox.presentation.viewmodel

import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zoomerbox.data.repository.IShoppingCartRepository
import com.zoomerbox.model.item.ShoppingCartItem
import com.zoomerbox.presentation.view.util.ISchedulersProvider
import io.reactivex.Single
import io.reactivex.disposables.Disposable

class ShoppingCartViewModel(
    @NonNull private val repository: IShoppingCartRepository,
    @NonNull private val schedulersProvider: ISchedulersProvider
) : ViewModel() {

    private val progressLiveData = MutableLiveData<Boolean>()
    private val errorLiveData = MutableLiveData<Throwable>()
    private val cartItemsLiveData = MutableLiveData<List<ShoppingCartItem>>()
    private var disposable: Disposable? = null

    fun loadShoppingCartItems() {
        disposable = Single.fromCallable { repository.getShoppingCartItems() }
            .doOnSubscribe { progressLiveData.postValue(true) }
            .doOnTerminate { progressLiveData.postValue(false) }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .subscribe({ cartItems ->
                Log.d(
                    TAG,
                    "${repository.getImplName()} successfully returned shopping cart items: $cartItems"
                )
                cartItemsLiveData.postValue(cartItems)
            }, { ex ->
                Log.e(
                    TAG,
                    "${repository.getImplName()} failed to return the shopping cart items with the exception: ${ex.message}"
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

    fun getCartItemsLiveData(): LiveData<List<ShoppingCartItem>> {
        return cartItemsLiveData
    }

    companion object {
        val TAG: String = this::class.java.declaringClass.simpleName
    }
}