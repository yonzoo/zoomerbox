package com.zoomerbox.presentation.viewmodel

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

class UserViewModel(
    @NonNull private val repository: IUserRepository,
    @NonNull private val schedulersProvider: ISchedulersProvider
) : ViewModel() {

    private val progressLiveData = MutableLiveData<Boolean>()
    private val errorLiveData = MutableLiveData<Throwable>()
    private val userLiveData = MutableLiveData<User>()
    private var disposable: Disposable? = null

    fun loadUser() {
        val user = FirebaseAuth.getInstance().currentUser
        disposable = repository.getUser(user!!.uid, user.phoneNumber!!)
            .doOnSubscribe { progressLiveData.postValue(true) }
            .doOnTerminate { progressLiveData.postValue(false) }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .subscribe({ user ->
                Log.d(
                    TAG,
                    "${repository.getImplName()} successfully returned the user: $user"
                )
                userLiveData.postValue(user)
            }, { ex ->
                Log.e(
                    TAG,
                    "${repository.getImplName()} failed to return the user's body with the exception: ${ex.message}"
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
