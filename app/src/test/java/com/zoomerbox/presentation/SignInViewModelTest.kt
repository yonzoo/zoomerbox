package com.zoomerbox.presentation

import android.app.Activity
import android.os.Parcel
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.zoomerbox.presentation.view.util.ISchedulersProvider
import com.zoomerbox.presentation.viewmodel.SignInViewModel
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Executor

@RunWith(MockitoJUnitRunner::class)
class SignInViewModelTest {

    @get:Rule
    val rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var firebaseAuth: FirebaseAuth

    private lateinit var credential: PhoneAuthCredential

    @Mock
    lateinit var schedulersProvider: ISchedulersProvider

    @Mock
    lateinit var progressLiveDataObserver: Observer<Boolean>

    @Mock
    lateinit var errorLiveDataObserver: Observer<Throwable>

    @Mock
    lateinit var resultLiveDataObserver: Observer<AuthResult>

    @Mock
    lateinit var redirectLiveDataObserver: Observer<String>

    lateinit var signInViewModel: SignInViewModel

    @Before
    fun setUp() {
        Mockito.`when`(schedulersProvider.io()).thenReturn(Schedulers.trampoline())
        Mockito.`when`(schedulersProvider.ui()).thenReturn(Schedulers.trampoline())
        signInViewModel = SignInViewModel(firebaseAuth)
        signInViewModel.getErrorLiveData().observeForever(errorLiveDataObserver)
        signInViewModel.getProgressLiveData().observeForever(progressLiveDataObserver)
        signInViewModel.getRedirectLiveData().observeForever(redirectLiveDataObserver)
        signInViewModel.getResultLiveData().observeForever(resultLiveDataObserver)
        credential = PhoneAuthCredential.zzc("", "")
    }

    @Test
    fun verifyPhoneNumberTestFailed() {
        val expectedException = FirebaseException("Fail")

        signInViewModel.getCallbacks().onVerificationFailed(expectedException)

        val inOrder =
            Mockito.inOrder(progressLiveDataObserver, errorLiveDataObserver)
        inOrder.verify(progressLiveDataObserver).onChanged(false)
        inOrder.verify(errorLiveDataObserver).onChanged(expectedException)
    }

    @Test
    fun verifyPhoneNumberTestOnCodeSent() {

    }

    @Test
    fun verifyPhoneNumberTestCompleted() {
        val expectedResult = object : AuthResult {
            override fun describeContents(): Int {
                return 0
            }

            override fun writeToParcel(p0: Parcel?, p1: Int) {}

            override fun getAdditionalUserInfo(): AdditionalUserInfo? {
                return null
            }

            override fun getCredential(): AuthCredential? {
                return null
            }

            override fun getUser(): FirebaseUser? {
                return null
            }

        }

        Mockito.`when`(firebaseAuth.signInWithCredential(credential)).thenReturn(object :
            Task<AuthResult>() {
            override fun isComplete(): Boolean {
                return true
            }

            override fun isSuccessful(): Boolean {
                return true
            }

            override fun isCanceled(): Boolean {
                return false
            }

            override fun addOnCompleteListener(p0: OnCompleteListener<AuthResult>): Task<AuthResult> {
                p0.onComplete(this)
                return this
            }

            override fun getResult(): AuthResult? {
                return expectedResult
            }

            override fun <X : Throwable?> getResult(p0: Class<X>): AuthResult? {
                return expectedResult
            }

            override fun getException(): Exception? {
                return null
            }

            override fun addOnSuccessListener(p0: OnSuccessListener<in AuthResult>): Task<AuthResult> {
                p0.onSuccess(result)
                return this
            }

            override fun addOnSuccessListener(
                p0: Executor,
                p1: OnSuccessListener<in AuthResult>
            ): Task<AuthResult> {
                return this
            }

            override fun addOnSuccessListener(
                p0: Activity,
                p1: OnSuccessListener<in AuthResult>
            ): Task<AuthResult> {
                return this
            }

            override fun addOnFailureListener(p0: OnFailureListener): Task<AuthResult> {
                return this
            }

            override fun addOnFailureListener(
                p0: Executor,
                p1: OnFailureListener
            ): Task<AuthResult> {
                return this
            }

            override fun addOnFailureListener(
                p0: Activity,
                p1: OnFailureListener
            ): Task<AuthResult> {
                return this
            }
        })

        signInViewModel.getCallbacks().onVerificationCompleted(credential)

        val inOrder =
            Mockito.inOrder(progressLiveDataObserver, resultLiveDataObserver)
        inOrder.verify(progressLiveDataObserver).onChanged(false)
        inOrder.verify(resultLiveDataObserver).onChanged(expectedResult)
    }
}