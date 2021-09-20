package com.zoomerbox.presentation.viewmodel

import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.zoomerbox.data.repository.IFavouriteRepository
import com.zoomerbox.data.repository.IShoppingCartRepository
import com.zoomerbox.model.app.ShoppingCartItem
import com.zoomerbox.model.app.ZoomerBox
import com.zoomerbox.presentation.view.util.ISchedulersProvider
import io.reactivex.disposables.Disposable

class ZoomerBoxViewModel(
    @NonNull private val shoppingCartRepository: IShoppingCartRepository,
    @NonNull private val favouriteRepository: IFavouriteRepository,
    @NonNull private val schedulersProvider: ISchedulersProvider
) : ViewModel() {

    private val progressLiveData = MutableLiveData<Boolean>()
    private val errorLiveData = MutableLiveData<Throwable>()
    private val resultLiveData = MutableLiveData<List<ShoppingCartItem>>()
    private val isBoxFavouriteLiveData = MutableLiveData<Boolean>()
    private var favouriteDisposable: Disposable? = null
    private var addItemDisposable: Disposable? = null

    fun isBoxFavourite(boxName: String) {
        val authUser = FirebaseAuth.getInstance().currentUser
        favouriteDisposable =
            favouriteRepository.isBoxFavourite(authUser!!.uid, boxName)
                .doOnSubscribe { progressLiveData.postValue(true) }
                .doOnTerminate { progressLiveData.postValue(false) }
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .subscribe({ isBoxFavourite ->
                    Log.d(
                        TAG,
                        "${favouriteRepository.getImplName()} successfully returned the favourite flag and it is: $isBoxFavourite"
                    )
                    isBoxFavouriteLiveData.postValue(isBoxFavourite)
                }, { ex ->
                    Log.e(
                        TAG,
                        "${favouriteRepository.getImplName()} failed to return the favourite flag with the exception: ${ex.message}"
                    )
                    errorLiveData.postValue(ex)
                })
    }

    fun toggleBoxFavourite(boxName: String) {
        val authUser = FirebaseAuth.getInstance().currentUser
        favouriteDisposable =
            favouriteRepository.toggleFavouriteBox(authUser!!.uid, boxName)
                .doOnSubscribe { progressLiveData.postValue(true) }
                .doOnTerminate { progressLiveData.postValue(false) }
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .subscribe({ isBoxFavourite ->
                    Log.d(
                        TAG,
                        "${favouriteRepository.getImplName()} successfully returned the favourite flag and it is: $isBoxFavourite"
                    )
                    isBoxFavouriteLiveData.postValue(isBoxFavourite)
                }, { ex ->
                    Log.e(
                        TAG,
                        "${favouriteRepository.getImplName()} failed to return the favourite flag with the exception: ${ex.message}"
                    )
                    errorLiveData.postValue(ex)
                })
    }

    fun addToCart(box: ZoomerBox) {
        val authUser = FirebaseAuth.getInstance().currentUser
        addItemDisposable = shoppingCartRepository.addShoppingCartItem(
            authUser!!.uid,
            ShoppingCartItem(box, false, 1, false)
        )
            .doOnSubscribe { progressLiveData.postValue(true) }
            .doOnTerminate { progressLiveData.postValue(false) }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .subscribe({ cartItems ->
                Log.d(
                    TAG,
                    "${shoppingCartRepository.getImplName()} successfully returned the cart items: $cartItems"
                )
                resultLiveData.postValue(cartItems)
            }, { ex ->
                Log.e(
                    TAG,
                    "${shoppingCartRepository.getImplName()} failed to return the cart items with the exception: ${ex.message}"
                )
                errorLiveData.postValue(ex)
            })
    }

    override fun onCleared() {
        super.onCleared()
        favouriteDisposable?.dispose()
        addItemDisposable?.dispose()
        favouriteDisposable = null
        addItemDisposable = null
    }

    fun getProgressLiveData(): LiveData<Boolean> {
        return progressLiveData
    }

    fun getErrorLiveData(): LiveData<Throwable> {
        return errorLiveData
    }

    fun getResultLiveData(): LiveData<List<ShoppingCartItem>> {
        return resultLiveData
    }

    fun getFavouriteLiveData(): LiveData<Boolean> {
        return isBoxFavouriteLiveData
    }

    companion object {
        val TAG: String = this::class.java.declaringClass.simpleName
    }
}
