package com.zoomerbox.presentation.viewmodel

import android.security.keystore.UserNotAuthenticatedException
import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.zoomerbox.domain.IUserInteractor
import com.zoomerbox.model.app.User
import com.zoomerbox.presentation.view.util.ISchedulersProvider
import io.reactivex.disposables.Disposable

/**
 * ViewModel для базового экрана в котором происходит аутентификация и редирект пользователя
 *
 * @param interactor интерактор для обработки данных пользователя
 * @param schedulersProvider провайдер доступа к потокам
 */
class DefaultViewModel(
    @NonNull private val interactor: IUserInteractor,
    @NonNull private val schedulersProvider: ISchedulersProvider
) : ViewModel() {

    private val progressLiveData = MutableLiveData<Boolean>()
    private val errorLiveData = MutableLiveData<Throwable>()
    private val userLiveData = MutableLiveData<User>()
    private var disposable: Disposable? = null
    private var userDisposable: Disposable? = null

    fun authenticate() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            errorLiveData.postValue(UserNotAuthenticatedException())
        } else {
            user.getIdToken(true).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    if (result != null) {
                        result.token?.let {
                            getUserCredentials(it, user.phoneNumber!!)
                        }
                    }
                } else {
                    errorLiveData.postValue(UserNotAuthenticatedException())
                }
            }
            if (user.phoneNumber == null) {
                errorLiveData.postValue(UserNotAuthenticatedException())
            }
        }
    }

    private fun getUserCredentials(token: String, phoneNumber: String) {
        disposable = interactor.getToken(token, phoneNumber)
            .doOnSubscribe { progressLiveData.postValue(true) }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .subscribe({ result ->
                Log.d(TAG, "Successfully got user data: $result")
                userDisposable = interactor.getUser()
                    .doOnSubscribe { progressLiveData.postValue(true) }
                    .doOnTerminate { progressLiveData.postValue(false) }
                    .subscribeOn(schedulersProvider.io())
                    .observeOn(schedulersProvider.ui())
                    .subscribe({ result ->
                        Log.d(TAG, "Successfully got user data: $result")
                        userLiveData.postValue(result)
                    }, { ex ->
                        Log.e(TAG, "Failed to get user data with exception: $ex")
                        errorLiveData.postValue(ex)
                    })
            }, { ex ->
                Log.e(TAG, "Failed to get token with exception: $ex")
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

    fun getUserLiveData(): LiveData<User> {
        return userLiveData
    }

    companion object {
        val TAG: String = this::class.java.declaringClass.simpleName
    }
}