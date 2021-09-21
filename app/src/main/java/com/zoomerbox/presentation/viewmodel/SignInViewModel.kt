package com.zoomerbox.presentation.viewmodel

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

class SignInViewModel(private val auth: FirebaseAuth) : ViewModel() {

    private val progressLiveData = MutableLiveData<Boolean>()
    private val errorLiveData = MutableLiveData<Throwable>()
    private val resultLiveData = MutableLiveData<AuthResult>()
    private val redirectLiveData = MutableLiveData<String>()
    private val callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks = getCallbacks()

    fun verifyPhoneNumber(phoneNumber: String, activityRef: WeakReference<Activity>) {
        progressLiveData.postValue(true)
        activityRef.get()?.let {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                it,
                callbacks
            )
        }
    }

    fun verifyWithCode(verificationId: String, verificationCode: String) {
        progressLiveData.postValue(true)
        val credential: PhoneAuthCredential =
            PhoneAuthProvider.getCredential(verificationId, verificationCode)
        signInWithCredential(credential)
    }

    fun getCallbacks(): PhoneAuthProvider.OnVerificationStateChangedCallbacks {
        return object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithCredential(credential)
            }

            override fun onVerificationFailed(exception: FirebaseException) {
                progressLiveData.postValue(false)
                errorLiveData.postValue(exception)
            }

            override fun onCodeSent(
                verificationId: String,
                param1: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(verificationId, param1)
                redirectLiveData.postValue(verificationId)
                progressLiveData.postValue(false)
            }
        }
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task: Task<AuthResult> ->
                progressLiveData.postValue(false)
                if (task.isSuccessful) {
                    resultLiveData.postValue(task.result)
                } else {
                    resultLiveData.postValue(null)
                }
            }
    }

    /**
     * @return LiveData с флагом загрузки данных.
     */
    fun getProgressLiveData(): LiveData<Boolean> {
        return progressLiveData
    }

    /**
     * @return LiveData с ошибкой.
     */
    fun getErrorLiveData(): LiveData<Throwable> {
        return errorLiveData
    }

    /**
     * @return LiveData с результатом аутентификации.
     */
    fun getResultLiveData(): LiveData<AuthResult> {
        return resultLiveData
    }

    /**
     * @return LiveData с флагом редиректа на активити для ввода кода из СМС.
     */
    fun getRedirectLiveData(): LiveData<String> {
        return redirectLiveData
    }

    companion object {
        val TAG: String = this::class.java.declaringClass.simpleName
    }
}
