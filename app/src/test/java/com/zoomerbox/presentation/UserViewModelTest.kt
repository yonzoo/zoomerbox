package com.zoomerbox.presentation

import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth
import com.google.firebase.auth.FirebaseAuth
import com.zoomerbox.data.repository.ICacheRepository
import com.zoomerbox.data.repository.IUserRepository
import com.zoomerbox.model.app.User
import com.zoomerbox.model.dto.UserDTO
import com.zoomerbox.presentation.view.util.ISchedulersProvider
import com.zoomerbox.presentation.viewmodel.UserViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class UserViewModelTest {

    @get:Rule
    val rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var schedulersProvider: ISchedulersProvider

    @Mock
    lateinit var sharedPreferences: SharedPreferences

    @Mock
    lateinit var cacheRepository: ICacheRepository

    @Mock
    lateinit var progressLiveDataObserver: Observer<Boolean>

    @Mock
    lateinit var errorLiveDataObserver: Observer<Throwable>

    @Mock
    lateinit var userLiveDataObserver: Observer<User>

    @Mock
    lateinit var exitLiveDataObserver: Observer<Boolean>

    @Mock
    lateinit var userRepository: IUserRepository

    lateinit var userViewModel: UserViewModel

    @Before
    fun setUp() {
        Mockito.`when`(schedulersProvider.io()).thenReturn(Schedulers.trampoline())
        Mockito.`when`(schedulersProvider.ui()).thenReturn(Schedulers.trampoline())
        mockkStatic(FirebaseAuth::class)
        every { FirebaseAuth.getInstance() } returns mockk(relaxed = true)
        every { FirebaseAuth.getInstance().currentUser } returns null
        userViewModel =
            UserViewModel(userRepository, cacheRepository, schedulersProvider, sharedPreferences)
        userViewModel.getErrorLiveData().observeForever(errorLiveDataObserver)
        userViewModel.getProgressLiveData().observeForever(progressLiveDataObserver)
        userViewModel.getUserLiveData().observeForever(userLiveDataObserver)
        userViewModel.getExitLiveData().observeForever(exitLiveDataObserver)
    }

    @Test
    fun setDisposableToNullWhenViewModelCleared() {
        userViewModel.disposable = Single.fromCallable { null }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .subscribe({
                Truth.assertThat(userViewModel.disposable).isNotNull()
                userViewModel.clearViewModel()
                Truth.assertThat(userViewModel.disposable).isNull()
            }, {
                // do nothing
            })
    }

    @Test
    fun loadUserFinishedSuccessfully() {
        val expectedUser = User.buildFromDTO(
            UserDTO(
                TEST_ID,
                TEST_USERNAME,
                TEST_PHONE,
                TEST_AVATAR_URL
            )
        )
        Mockito.`when`(userRepository.getUserFromPreferences("", ""))
            .thenReturn(Single.fromCallable { expectedUser })

        userViewModel.loadUser()

        val inOrder =
            Mockito.inOrder(progressLiveDataObserver, userLiveDataObserver)
        inOrder.verify(progressLiveDataObserver).onChanged(false)
        inOrder.verify(userLiveDataObserver).onChanged(expectedUser)
    }

    @Test
    fun loadUserFailed() {
        val expectedException = RuntimeException("Fail")
        Mockito.`when`(userRepository.getUserFromPreferences("", ""))
            .thenReturn(Single.error(expectedException))

        userViewModel.loadUser()

        val inOrder =
            Mockito.inOrder(progressLiveDataObserver, errorLiveDataObserver)
        inOrder.verify(progressLiveDataObserver).onChanged(false)
        inOrder.verify(errorLiveDataObserver).onChanged(expectedException)
    }

    companion object {
        const val TEST_ID = "test"
        const val TEST_USERNAME = "test"
        const val TEST_PHONE = "test"
        const val TEST_AVATAR_URL = "test"
    }
}
