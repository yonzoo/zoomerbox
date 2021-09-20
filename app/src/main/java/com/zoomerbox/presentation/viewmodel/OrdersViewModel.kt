package com.zoomerbox.presentation.viewmodel

import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.zoomerbox.data.repository.IOrdersRepository
import com.zoomerbox.model.app.Order
import com.zoomerbox.presentation.view.util.ISchedulersProvider
import io.reactivex.disposables.Disposable

class OrdersViewModel(
    @NonNull private val repository: IOrdersRepository,
    @NonNull private val schedulersProvider: ISchedulersProvider
) : ViewModel() {

    private val progressLiveData = MutableLiveData<Boolean>()
    private val errorLiveData = MutableLiveData<Throwable>()
    private val ordersLiveData = MutableLiveData<List<Order>>()
    private var disposable: Disposable? = null

    fun loadOrders() {
        val authUser = FirebaseAuth.getInstance().currentUser
        disposable = repository.getOrders(authUser!!.uid)
            .doOnSubscribe { progressLiveData.postValue(true) }
            .doOnTerminate { progressLiveData.postValue(false) }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .subscribe({ orders ->
                Log.d(
                    TAG,
                    "${repository.getImplName()} successfully returned the orders: $orders"
                )
                ordersLiveData.postValue(orders)
            }, { ex ->
                Log.e(
                    TAG,
                    "${repository.getImplName()} failed to return the orders with the exception: ${ex.message}"
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

    fun getOrdersLiveData(): LiveData<List<Order>> {
        return ordersLiveData
    }

    companion object {
        val TAG: String = this::class.java.declaringClass.simpleName
    }
}
