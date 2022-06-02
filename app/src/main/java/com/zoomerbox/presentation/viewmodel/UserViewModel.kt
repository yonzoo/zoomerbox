package com.zoomerbox.presentation.viewmodel

import android.content.SharedPreferences
import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.zoomerbox.data.repository.ICacheRepository
import com.zoomerbox.data.repository.IUserRepository
import com.zoomerbox.model.app.User
import com.zoomerbox.presentation.view.util.ISchedulersProvider
import io.reactivex.disposables.Disposable

/**
 *
 * ViewModel для экрана с информацией о пользователе.
 *
 * @param repository репозиторий с данными о пользователе
 * @param cacheRepository репозиторий с данными из кеша
 * @param schedulersProvider провайдер доступа к потокам
 * @param sharedPreferences объект с префами
 */
class UserViewModel(
    @NonNull private val repository: IUserRepository,
    @NonNull private val cacheRepository: ICacheRepository,
    @NonNull private val schedulersProvider: ISchedulersProvider,
    @NonNull private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val progressLiveData = MutableLiveData<Boolean>()
    private val errorLiveData = MutableLiveData<Throwable>()
    private val userLiveData = MutableLiveData<User>()
    private val exitLiveData = MutableLiveData<Boolean>()
    var disposable: Disposable? = null

    fun loadUser() {
        val authUser = FirebaseAuth.getInstance().currentUser
        val uid = authUser?.uid ?: ""
        val phoneNumber = authUser?.phoneNumber ?: ""
        disposable = repository.getUserFromPreferences(uid, phoneNumber)
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

    fun signOut() {
        cacheRepository.clearCache()
        sharedPreferences.edit().clear().apply()
        FirebaseAuth.getInstance().signOut()
        exitLiveData.postValue(true)
    }

    fun clearViewModel() {
        onCleared()
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

    fun getExitLiveData(): LiveData<Boolean> {
        return exitLiveData
    }

    companion object {
        val TAG: String = this::class.java.declaringClass.simpleName
    }
}
