package com.zoomerbox.presentation.viewmodel

import android.security.keystore.UserNotAuthenticatedException
import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.zoomerbox.data.repository.IUserRepository
import com.zoomerbox.model.app.User
import com.zoomerbox.presentation.view.util.ISchedulersProvider
import io.reactivex.disposables.Disposable

class DefaultViewModel(
    @NonNull private val repository: IUserRepository,
    @NonNull private val schedulersProvider: ISchedulersProvider
) : ViewModel() {

    private val progressLiveData = MutableLiveData<Boolean>()
    private val errorLiveData = MutableLiveData<Throwable>()
    private val userLiveData = MutableLiveData<User>()
    private var disposable: Disposable? = null

    fun authenticate() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            errorLiveData.postValue(UserNotAuthenticatedException())
        } else {
            if (user.phoneNumber == null) {
                errorLiveData.postValue(UserNotAuthenticatedException())
            }
            getUserCredentials(user.uid, user.phoneNumber!!)
        }
    }

    private fun getUserCredentials(uid: String, phoneNumber: String) {
        disposable =
            repository.getUser(uid, phoneNumber)
                .doOnSubscribe { progressLiveData.postValue(true) }
                .doOnTerminate { progressLiveData.postValue(false) }
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .subscribe({ result ->
                    Log.d(
                        TAG,
                        "${repository.getImplName()} successfully returned the user: $result"
                    )
                    userLiveData.postValue(result)
                }, { ex ->
                    Log.e(
                        TAG,
                        "${repository.getImplName()} failed to get the result with the exception: ${ex.message}"
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

    fun getUserLiveData(): LiveData<User> {
        return userLiveData
    }

    companion object {
        val TAG: String = this::class.java.declaringClass.simpleName
    }
}