package com.zoomerbox.presentation.viewmodel

import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.zoomerbox.data.repository.IOrdersRepository
import com.zoomerbox.model.app.Order
import com.zoomerbox.model.app.OrderBox
import com.zoomerbox.presentation.view.util.ISchedulersProvider
import io.reactivex.disposables.Disposable

class CreateOrderViewModel(
    @NonNull private val repository: IOrdersRepository,
    @NonNull private val schedulersProvider: ISchedulersProvider
) : ViewModel() {

    private val progressLiveData = MutableLiveData<Boolean>()
    private val errorLiveData = MutableLiveData<Throwable>()
    private val resultLiveData = MutableLiveData<Order>()
    private var disposable: Disposable? = null

    fun createOrder(
        cityName: String,
        fullName: String,
        postIndex: String,
        orderItems: List<OrderBox>
    ) {
        val authUser = FirebaseAuth.getInstance().currentUser
        disposable = repository.createOrder(
            authUser!!.uid,
            cityName,
            fullName,
            postIndex,
            orderItems
        )
            .doOnSubscribe { progressLiveData.postValue(true) }
            .doOnTerminate { progressLiveData.postValue(false) }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .subscribe({ order ->
                Log.d(
                    TAG,
                    "${repository.getImplName()} successfully returned the created order: $order"
                )
                resultLiveData.postValue(order)
            }, { ex ->
                Log.e(
                    TAG,
                    "${repository.getImplName()} failed to create the order with the exception: ${ex.message}"
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

    fun getOrderLiveData(): LiveData<Order> {
        return resultLiveData
    }

    companion object {
        val TAG: String = this::class.java.declaringClass.simpleName
    }
}