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
import com.zoomerbox.presentation.view.util.ISchedulersProvider
import io.reactivex.disposables.Disposable

class ShoppingCartViewModel(
    @NonNull private val shoppingCartRepository: IShoppingCartRepository,
    @NonNull private val favouriteRepository: IFavouriteRepository,
    @NonNull private val schedulersProvider: ISchedulersProvider
) : ViewModel() {

    private val progressLiveData = MutableLiveData<Boolean>()
    private val errorLiveData = MutableLiveData<Throwable>()
    private val cartItemsLiveData = MutableLiveData<List<ShoppingCartItem>>()
    private val isBoxFavouriteLiveData = MutableLiveData<Boolean>()
    private val cartItemsSelectedLiveData = MutableLiveData<List<ShoppingCartItem>>()
    private var disposable: Disposable? = null
    var updatedFavouriteItem: ShoppingCartItem? = null
    var updatedSelectedItem: ShoppingCartItem? = null

    fun loadShoppingCartItems() {
        val user = FirebaseAuth.getInstance().currentUser
        disposable = shoppingCartRepository.getShoppingCartItems(user!!.uid)
            .doOnSubscribe { progressLiveData.postValue(true) }
            .doOnTerminate { progressLiveData.postValue(false) }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .subscribe({ cartItems ->
                Log.d(
                    TAG,
                    "${shoppingCartRepository.getImplName()} successfully returned shopping cart items: $cartItems"
                )
                cartItemsLiveData.postValue(cartItems)
            }, { ex ->
                Log.e(
                    TAG,
                    "${shoppingCartRepository.getImplName()} failed to return the shopping cart items with the exception: ${ex.message}"
                )
                errorLiveData.postValue(ex)
            })
    }

    fun toggleBoxFavourite(shoppingCartItem: ShoppingCartItem) {
        val authUser = FirebaseAuth.getInstance().currentUser
        updatedFavouriteItem = shoppingCartItem;
        disposable =
            favouriteRepository.toggleFavouriteBox(authUser!!.uid, shoppingCartItem.box.name)
                .doOnSubscribe { progressLiveData.postValue(true) }
                .doOnTerminate { progressLiveData.postValue(false) }
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .subscribe({ isBoxFavourite ->
                    Log.d(
                        ZoomerBoxViewModel.TAG,
                        "${favouriteRepository.getImplName()} successfully returned the favourite flag and it is: $isBoxFavourite"
                    )
                    isBoxFavouriteLiveData.postValue(isBoxFavourite)
                }, { ex ->
                    Log.e(
                        ZoomerBoxViewModel.TAG,
                        "${favouriteRepository.getImplName()} failed to return the favourite flag with the exception: ${ex.message}"
                    )
                    errorLiveData.postValue(ex)
                })
    }

    fun addShoppingCartItem(shoppingCartItem: ShoppingCartItem) {
        val authUser = FirebaseAuth.getInstance().currentUser
        disposable = shoppingCartRepository.addShoppingCartItem(authUser!!.uid, shoppingCartItem)
            .doOnSubscribe { progressLiveData.postValue(true) }
            .doOnTerminate { progressLiveData.postValue(false) }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .subscribe({ cartItems ->
                Log.d(
                    ZoomerBoxViewModel.TAG,
                    "${shoppingCartRepository.getImplName()} successfully returned the cart items and it is: $cartItems"
                )
                cartItemsLiveData.postValue(cartItems)
            }, { ex ->
                Log.e(
                    ZoomerBoxViewModel.TAG,
                    "${shoppingCartRepository.getImplName()} failed to return the cart items with the exception: ${ex.message}"
                )
                errorLiveData.postValue(ex)
            })
    }

    fun removeSingleShoppingCartItem(shoppingCartItem: ShoppingCartItem) {
        val authUser = FirebaseAuth.getInstance().currentUser
        disposable =
            shoppingCartRepository.reduceCountOfShoppingCartItems(authUser!!.uid, shoppingCartItem)
                .doOnSubscribe { progressLiveData.postValue(true) }
                .doOnTerminate { progressLiveData.postValue(false) }
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .subscribe({ cartItems ->
                    Log.d(
                        ZoomerBoxViewModel.TAG,
                        "${shoppingCartRepository.getImplName()} successfully returned the cart items and it is: $cartItems"
                    )
                    cartItemsLiveData.postValue(cartItems)
                }, { ex ->
                    Log.e(
                        ZoomerBoxViewModel.TAG,
                        "${shoppingCartRepository.getImplName()} failed to return the cart items with the exception: ${ex.message}"
                    )
                    errorLiveData.postValue(ex)
                })
    }

    fun deleteShoppingCartItem(shoppingCartItem: ShoppingCartItem) {
        val authUser = FirebaseAuth.getInstance().currentUser
        disposable = shoppingCartRepository.removeShoppingCartItem(authUser!!.uid, shoppingCartItem)
            .doOnSubscribe { progressLiveData.postValue(true) }
            .doOnTerminate { progressLiveData.postValue(false) }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .subscribe({ cartItems ->
                Log.d(
                    ZoomerBoxViewModel.TAG,
                    "${shoppingCartRepository.getImplName()} successfully returned the cart items and it is: $cartItems"
                )
                cartItemsLiveData.postValue(cartItems)
            }, { ex ->
                Log.e(
                    ZoomerBoxViewModel.TAG,
                    "${shoppingCartRepository.getImplName()} failed to return the cart items with the exception: ${ex.message}"
                )
                errorLiveData.postValue(ex)
            })
    }

    fun deleteAllShoppingCartItems(shoppingCartItems: List<ShoppingCartItem>) {
        val authUser = FirebaseAuth.getInstance().currentUser
        disposable =
            shoppingCartRepository.removeShoppingCartItems(authUser!!.uid, shoppingCartItems)
                .doOnSubscribe { progressLiveData.postValue(true) }
                .doOnTerminate { progressLiveData.postValue(false) }
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .subscribe({ cartItems ->
                    Log.d(
                        ZoomerBoxViewModel.TAG,
                        "${shoppingCartRepository.getImplName()} successfully returned the cart items and it is: $cartItems"
                    )
                    cartItemsLiveData.postValue(cartItems)
                }, { ex ->
                    Log.e(
                        ZoomerBoxViewModel.TAG,
                        "${shoppingCartRepository.getImplName()} failed to return the cart items with the exception: ${ex.message}"
                    )
                    errorLiveData.postValue(ex)
                })
    }

    fun toggleSelectShoppingCartItem(shoppingCartItem: ShoppingCartItem) {
        val authUser = FirebaseAuth.getInstance().currentUser
        updatedSelectedItem = shoppingCartItem
        disposable =
            shoppingCartRepository.toggleSelectShoppingCartItem(authUser!!.uid, shoppingCartItem)
                .doOnSubscribe { progressLiveData.postValue(true) }
                .doOnTerminate { progressLiveData.postValue(false) }
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .subscribe({ cartItems ->
                    Log.d(
                        ZoomerBoxViewModel.TAG,
                        "${shoppingCartRepository.getImplName()} successfully returned the cart items and it is: $cartItems"
                    )
                    cartItemsSelectedLiveData.postValue(cartItems)
                }, { ex ->
                    Log.e(
                        ZoomerBoxViewModel.TAG,
                        "${shoppingCartRepository.getImplName()} failed to return the cart items with the exception: ${ex.message}"
                    )
                    errorLiveData.postValue(ex)
                })
    }

    fun selectAllShoppingCartItems(shoppingCartItems: List<ShoppingCartItem>) {
        val authUser = FirebaseAuth.getInstance().currentUser
        disposable =
            shoppingCartRepository.selectAllShoppingCartItems(authUser!!.uid, shoppingCartItems)
                .doOnSubscribe { progressLiveData.postValue(true) }
                .doOnTerminate { progressLiveData.postValue(false) }
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .subscribe({ cartItems ->
                    Log.d(
                        ZoomerBoxViewModel.TAG,
                        "${shoppingCartRepository.getImplName()} successfully returned the cart items and it is: $cartItems"
                    )
                    cartItemsLiveData.postValue(cartItems)
                }, { ex ->
                    Log.e(
                        ZoomerBoxViewModel.TAG,
                        "${shoppingCartRepository.getImplName()} failed to return the cart items with the exception: ${ex.message}"
                    )
                    errorLiveData.postValue(ex)
                })
    }

    fun deselectAllShoppingCartItems(shoppingCartItems: List<ShoppingCartItem>) {
        val authUser = FirebaseAuth.getInstance().currentUser
        disposable =
            shoppingCartRepository.deselectAllShoppingCartItems(authUser!!.uid, shoppingCartItems)
                .doOnSubscribe { progressLiveData.postValue(true) }
                .doOnTerminate { progressLiveData.postValue(false) }
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .subscribe({ cartItems ->
                    Log.d(
                        ZoomerBoxViewModel.TAG,
                        "${shoppingCartRepository.getImplName()} successfully returned the cart items and it is: $cartItems"
                    )
                    cartItemsLiveData.postValue(cartItems)
                }, { ex ->
                    Log.e(
                        ZoomerBoxViewModel.TAG,
                        "${shoppingCartRepository.getImplName()} failed to return the cart items with the exception: ${ex.message}"
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

    fun getIsBoxFavouriteLiveData(): LiveData<Boolean> {
        return isBoxFavouriteLiveData
    }

    fun getCartItemsSelectedLiveData(): LiveData<List<ShoppingCartItem>> {
        return cartItemsSelectedLiveData
    }

    companion object {
        val TAG: String = this::class.java.declaringClass.simpleName
    }
}