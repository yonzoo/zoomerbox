package com.zoomerbox.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth
import com.zoomerbox.domain.ISeasonDropInteractor
import com.zoomerbox.data.utils.MockDataProvider
import com.zoomerbox.model.app.SeasonDrop
import com.zoomerbox.model.dto.SeasonDropDTO
import com.zoomerbox.presentation.view.util.ISchedulersProvider
import com.zoomerbox.presentation.viewmodel.ShopViewModel
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
class ShopViewModelTest {

    @get:Rule
    val rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var schedulersProvider: ISchedulersProvider

    @Mock
    lateinit var progressLiveDataObserver: Observer<Boolean>

    @Mock
    lateinit var errorLiveDataObserver: Observer<Throwable>

    @Mock
    lateinit var seasonDropLiveDataObserver: Observer<SeasonDrop>

    @Mock
    lateinit var interactor: ISeasonDropInteractor

    lateinit var shopViewModel: ShopViewModel

    @Before
    fun setUp() {
        Mockito.`when`(schedulersProvider.io()).thenReturn(Schedulers.trampoline())
        Mockito.`when`(schedulersProvider.ui()).thenReturn(Schedulers.trampoline())
        shopViewModel = ShopViewModel(interactor, schedulersProvider)
        shopViewModel.getErrorLiveData().observeForever(errorLiveDataObserver)
        shopViewModel.getProgressLiveData().observeForever(progressLiveDataObserver)
        shopViewModel.getSeasonDropLiveData().observeForever(seasonDropLiveDataObserver)
    }

    @Test
    fun setDisposableToNullWhenViewModelCleared() {
        shopViewModel.disposable = Single.fromCallable { null }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .subscribe({
                Truth.assertThat(shopViewModel.disposable).isNotNull()
                shopViewModel.clearViewModel()
                Truth.assertThat(shopViewModel.disposable).isNull()
            }, {
                // do nothing
            })
    }

    @Test
    fun loadSeasonDropFinishedSuccessfully() {
        val expectedDrop = SeasonDrop.buildFromDTO(
            SeasonDropDTO(
                0,
                TEST_URL,
                MockDataProvider.getCollections()
            )
        )
        Mockito.`when`(interactor.getSeasonDrop()).thenReturn(Single.fromCallable { expectedDrop })
        shopViewModel.loadSeasonDrop()

        val inOrder =
            Mockito.inOrder(progressLiveDataObserver, seasonDropLiveDataObserver)
        inOrder.verify(progressLiveDataObserver).onChanged(false)
        inOrder.verify(seasonDropLiveDataObserver).onChanged(expectedDrop)
    }

    @Test
    fun loadSeasonDropFailed() {
        val expectedException = RuntimeException("Fail")
        Mockito.`when`(interactor.getSeasonDrop()).thenReturn(Single.error(expectedException))
        shopViewModel.loadSeasonDrop()

        val inOrder =
            Mockito.inOrder(progressLiveDataObserver, errorLiveDataObserver)
        inOrder.verify(progressLiveDataObserver).onChanged(false)
        inOrder.verify(errorLiveDataObserver).onChanged(expectedException)
    }

    companion object {
        const val TEST_URL = "test"
    }
}
