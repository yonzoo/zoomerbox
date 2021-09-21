package com.zoomerbox.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth
import com.google.firebase.auth.FirebaseAuth
import com.zoomerbox.data.repository.IFavouriteRepository
import com.zoomerbox.data.repository.IShoppingCartRepository
import com.zoomerbox.data.utils.MockDataProvider
import com.zoomerbox.model.app.ShoppingCartItem
import com.zoomerbox.model.dto.ShoppingCartItemDTO
import com.zoomerbox.presentation.view.util.ISchedulersProvider
import com.zoomerbox.presentation.viewmodel.ShoppingCartViewModel
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
class ShoppingCartViewModelTest {

    @get:Rule
    val rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var schedulersProvider: ISchedulersProvider

    @Mock
    lateinit var progressLiveDataObserver: Observer<Boolean>

    @Mock
    lateinit var errorLiveDataObserver: Observer<Throwable>

    @Mock
    lateinit var cartItemsLiveDataObserver: Observer<List<ShoppingCartItem>>

    @Mock
    lateinit var cartItemsSelectedLiveDataObserver: Observer<List<ShoppingCartItem>>

    @Mock
    lateinit var isBoxFavouriteLiveDataObserver: Observer<Boolean>

    @Mock
    lateinit var shoppingCartRepository: IShoppingCartRepository

    @Mock
    lateinit var favouriteRepository: IFavouriteRepository

    lateinit var shoppingCartViewModel: ShoppingCartViewModel

    @Before
    fun setUp() {
        Mockito.`when`(schedulersProvider.io()).thenReturn(Schedulers.trampoline())
        Mockito.`when`(schedulersProvider.ui()).thenReturn(Schedulers.trampoline())
        mockkStatic(FirebaseAuth::class)
        every { FirebaseAuth.getInstance() } returns mockk(relaxed = true)
        every { FirebaseAuth.getInstance().currentUser } returns null
        shoppingCartViewModel =
            ShoppingCartViewModel(shoppingCartRepository, favouriteRepository, schedulersProvider)
        shoppingCartViewModel.getErrorLiveData().observeForever(errorLiveDataObserver)
        shoppingCartViewModel.getProgressLiveData().observeForever(progressLiveDataObserver)
        shoppingCartViewModel.getCartItemsLiveData().observeForever(cartItemsLiveDataObserver)
        shoppingCartViewModel.getCartItemsSelectedLiveData()
            .observeForever(cartItemsSelectedLiveDataObserver)
        shoppingCartViewModel.getIsBoxFavouriteLiveData()
            .observeForever(isBoxFavouriteLiveDataObserver)
    }

    @Test
    fun setDisposableToNullWhenViewModelCleared() {
        shoppingCartViewModel.disposable = Single.fromCallable { null }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .subscribe({
                Truth.assertThat(shoppingCartViewModel.disposable).isNotNull()
                shoppingCartViewModel.clearViewModel()
                Truth.assertThat(shoppingCartViewModel.disposable).isNull()
            }, {
                // do nothing
            })
    }

    @Test
    fun loadCartItemsFinishedSuccessfully() {
        val expectedCartItems = getShoppingCartItems()
        Mockito.`when`(shoppingCartRepository.getShoppingCartItems(""))
            .thenReturn(Single.fromCallable { expectedCartItems })

        shoppingCartViewModel.loadShoppingCartItems()

        val inOrder =
            Mockito.inOrder(progressLiveDataObserver, cartItemsLiveDataObserver)
        inOrder.verify(progressLiveDataObserver).onChanged(false)
        inOrder.verify(cartItemsLiveDataObserver).onChanged(expectedCartItems)
    }

    @Test
    fun loadCartItemsFailed() {
        val expectedException = RuntimeException("Fail")
        Mockito.`when`(shoppingCartRepository.getShoppingCartItems(""))
            .thenReturn(Single.error(expectedException))

        shoppingCartViewModel.loadShoppingCartItems()

        val inOrder =
            Mockito.inOrder(progressLiveDataObserver, errorLiveDataObserver)
        inOrder.verify(progressLiveDataObserver).onChanged(false)
        inOrder.verify(errorLiveDataObserver).onChanged(expectedException)
    }

    @Test
    fun toggleBoxFavouriteSuccessfully() {
        val expectedResult = true
        val shoppingCartItem = getShoppingCartItems()[0]
        Mockito.`when`(favouriteRepository.toggleFavouriteBox("", shoppingCartItem.box.name))
            .thenReturn(Single.fromCallable { expectedResult })

        Truth.assertThat(shoppingCartItem.isFavourite).isFalse()

        shoppingCartViewModel.toggleBoxFavourite(shoppingCartItem)

        val inOrder =
            Mockito.inOrder(progressLiveDataObserver, isBoxFavouriteLiveDataObserver)
        inOrder.verify(progressLiveDataObserver).onChanged(false)
        inOrder.verify(isBoxFavouriteLiveDataObserver).onChanged(true)
    }

    @Test
    fun toggleBoxFavouriteFailed() {
        val expectedException = RuntimeException("Fail")
        val shoppingCartItem = getShoppingCartItems()[0]
        Mockito.`when`(favouriteRepository.toggleFavouriteBox("", shoppingCartItem.box.name))
            .thenReturn(Single.error(expectedException))

        Truth.assertThat(shoppingCartItem.isFavourite).isFalse()

        shoppingCartViewModel.toggleBoxFavourite(shoppingCartItem)

        val inOrder =
            Mockito.inOrder(progressLiveDataObserver, errorLiveDataObserver)
        inOrder.verify(progressLiveDataObserver).onChanged(false)
        inOrder.verify(errorLiveDataObserver).onChanged(expectedException)
    }

    @Test
    fun addShoppingCartItemSuccessfully() {
        val expectedCartItems = getShoppingCartItems()
        val shoppingCartItem = getShoppingCartItems()[0]
        Mockito.`when`(shoppingCartRepository.addShoppingCartItem("", shoppingCartItem))
            .thenReturn(Single.fromCallable { expectedCartItems })

        shoppingCartViewModel.addShoppingCartItem(shoppingCartItem)

        val inOrder =
            Mockito.inOrder(progressLiveDataObserver, cartItemsLiveDataObserver)
        inOrder.verify(progressLiveDataObserver).onChanged(false)
        inOrder.verify(cartItemsLiveDataObserver).onChanged(expectedCartItems)
    }

    @Test
    fun addShoppingCartItemFailed() {
        val expectedException = RuntimeException("Fail")
        val shoppingCartItem = getShoppingCartItems()[0]
        Mockito.`when`(shoppingCartRepository.addShoppingCartItem("", shoppingCartItem))
            .thenReturn(Single.error(expectedException))

        shoppingCartViewModel.addShoppingCartItem(shoppingCartItem)

        val inOrder =
            Mockito.inOrder(progressLiveDataObserver, errorLiveDataObserver)
        inOrder.verify(progressLiveDataObserver).onChanged(false)
        inOrder.verify(errorLiveDataObserver).onChanged(expectedException)
    }

    @Test
    fun removeShoppingCartItemSuccessfully() {
        val expectedCartItems = getShoppingCartItems()
        val shoppingCartItem = getShoppingCartItems()[0]
        Mockito.`when`(shoppingCartRepository.reduceCountOfShoppingCartItems("", shoppingCartItem))
            .thenReturn(Single.fromCallable { expectedCartItems })

        shoppingCartViewModel.removeSingleShoppingCartItem(shoppingCartItem)

        val inOrder =
            Mockito.inOrder(progressLiveDataObserver, cartItemsLiveDataObserver)
        inOrder.verify(progressLiveDataObserver).onChanged(false)
        inOrder.verify(cartItemsLiveDataObserver).onChanged(expectedCartItems)
    }

    @Test
    fun removeShoppingCartItemFailed() {
        val expectedException = RuntimeException("Fail")
        val shoppingCartItem = getShoppingCartItems()[0]
        Mockito.`when`(shoppingCartRepository.reduceCountOfShoppingCartItems("", shoppingCartItem))
            .thenReturn(Single.error(expectedException))

        shoppingCartViewModel.removeSingleShoppingCartItem(shoppingCartItem)

        val inOrder =
            Mockito.inOrder(progressLiveDataObserver, errorLiveDataObserver)
        inOrder.verify(progressLiveDataObserver).onChanged(false)
        inOrder.verify(errorLiveDataObserver).onChanged(expectedException)
    }

    @Test
    fun deleteShoppingCartItemSuccessfully() {
        val expectedCartItems = getShoppingCartItems()
        val shoppingCartItem = getShoppingCartItems()[0]
        Mockito.`when`(shoppingCartRepository.removeShoppingCartItem("", shoppingCartItem))
            .thenReturn(Single.fromCallable { expectedCartItems })

        shoppingCartViewModel.deleteShoppingCartItem(shoppingCartItem)

        val inOrder =
            Mockito.inOrder(progressLiveDataObserver, cartItemsLiveDataObserver)
        inOrder.verify(progressLiveDataObserver).onChanged(false)
        inOrder.verify(cartItemsLiveDataObserver).onChanged(expectedCartItems)
    }

    @Test
    fun deleteShoppingCartItemFailed() {
        val expectedException = RuntimeException("Fail")
        val shoppingCartItem = getShoppingCartItems()[0]
        Mockito.`when`(shoppingCartRepository.removeShoppingCartItem("", shoppingCartItem))
            .thenReturn(Single.error(expectedException))

        shoppingCartViewModel.deleteShoppingCartItem(shoppingCartItem)

        val inOrder =
            Mockito.inOrder(progressLiveDataObserver, errorLiveDataObserver)
        inOrder.verify(progressLiveDataObserver).onChanged(false)
        inOrder.verify(errorLiveDataObserver).onChanged(expectedException)
    }

    @Test
    fun deleteAllShoppingCartItemsSuccessfully() {
        val expectedCartItems = getShoppingCartItems()
        Mockito.`when`(shoppingCartRepository.removeShoppingCartItems("", expectedCartItems))
            .thenReturn(Single.fromCallable { listOf() })

        shoppingCartViewModel.deleteAllShoppingCartItems(expectedCartItems)

        val inOrder =
            Mockito.inOrder(progressLiveDataObserver, cartItemsLiveDataObserver)
        inOrder.verify(progressLiveDataObserver).onChanged(false)
        inOrder.verify(cartItemsLiveDataObserver).onChanged(listOf())
    }

    @Test
    fun deleteAllShoppingCartItemsFailed() {
        val expectedException = RuntimeException("Fail")
        val cartItems = getShoppingCartItems()
        Mockito.`when`(shoppingCartRepository.removeShoppingCartItems("", cartItems))
            .thenReturn(Single.error(expectedException))

        shoppingCartViewModel.deleteAllShoppingCartItems(cartItems)

        val inOrder =
            Mockito.inOrder(progressLiveDataObserver, errorLiveDataObserver)
        inOrder.verify(progressLiveDataObserver).onChanged(false)
        inOrder.verify(errorLiveDataObserver).onChanged(expectedException)
    }

    @Test
    fun toggleSelectShoppingCartItemSuccessfully() {
        val expectedCartItems = getShoppingCartItems()
        val shoppingCartItem = getShoppingCartItems()[0]
        Mockito.`when`(shoppingCartRepository.toggleSelectShoppingCartItem("", shoppingCartItem))
            .thenReturn(Single.fromCallable { expectedCartItems })

        shoppingCartViewModel.toggleSelectShoppingCartItem(shoppingCartItem)

        val inOrder =
            Mockito.inOrder(progressLiveDataObserver, cartItemsSelectedLiveDataObserver)
        inOrder.verify(progressLiveDataObserver).onChanged(false)
        inOrder.verify(cartItemsSelectedLiveDataObserver).onChanged(expectedCartItems)
    }

    @Test
    fun toggleSelectShoppingCartItemFailed() {
        val expectedException = RuntimeException("Fail")
        val shoppingCartItem = getShoppingCartItems()[0]
        Mockito.`when`(shoppingCartRepository.toggleSelectShoppingCartItem("", shoppingCartItem))
            .thenReturn(Single.error(expectedException))

        shoppingCartViewModel.toggleSelectShoppingCartItem(shoppingCartItem)

        val inOrder =
            Mockito.inOrder(progressLiveDataObserver, errorLiveDataObserver)
        inOrder.verify(progressLiveDataObserver).onChanged(false)
        inOrder.verify(errorLiveDataObserver).onChanged(expectedException)
    }

    @Test
    fun selectAllCartItemsSuccessfully() {
        val expectedCartItems = getShoppingCartItems()
        Mockito.`when`(shoppingCartRepository.selectAllShoppingCartItems("", expectedCartItems))
            .thenReturn(Single.fromCallable { expectedCartItems })

        shoppingCartViewModel.selectAllShoppingCartItems(expectedCartItems)

        val inOrder =
            Mockito.inOrder(progressLiveDataObserver, cartItemsLiveDataObserver)
        inOrder.verify(progressLiveDataObserver).onChanged(false)
        inOrder.verify(cartItemsLiveDataObserver).onChanged(expectedCartItems)
    }

    @Test
    fun selectAllCartItemsFailed() {
        val expectedException = RuntimeException("Fail")
        val cartItems = getShoppingCartItems()
        Mockito.`when`(shoppingCartRepository.selectAllShoppingCartItems("", cartItems))
            .thenReturn(Single.error(expectedException))

        shoppingCartViewModel.selectAllShoppingCartItems(cartItems)

        val inOrder =
            Mockito.inOrder(progressLiveDataObserver, errorLiveDataObserver)
        inOrder.verify(progressLiveDataObserver).onChanged(false)
        inOrder.verify(errorLiveDataObserver).onChanged(expectedException)
    }

    @Test
    fun deselectAllCartItemsSuccessfully() {
        val expectedCartItems = getShoppingCartItems()
        Mockito.`when`(shoppingCartRepository.deselectAllShoppingCartItems("", expectedCartItems))
            .thenReturn(Single.fromCallable { expectedCartItems })

        shoppingCartViewModel.deselectAllShoppingCartItems(expectedCartItems)

        val inOrder =
            Mockito.inOrder(progressLiveDataObserver, cartItemsLiveDataObserver)
        inOrder.verify(progressLiveDataObserver).onChanged(false)
        inOrder.verify(cartItemsLiveDataObserver).onChanged(expectedCartItems)
    }

    @Test
    fun deselectAllCartItemsFailed() {
        val expectedException = RuntimeException("Fail")
        val cartItems = getShoppingCartItems()
        Mockito.`when`(shoppingCartRepository.deselectAllShoppingCartItems("", cartItems))
            .thenReturn(Single.error(expectedException))

        shoppingCartViewModel.deselectAllShoppingCartItems(cartItems)

        val inOrder =
            Mockito.inOrder(progressLiveDataObserver, errorLiveDataObserver)
        inOrder.verify(progressLiveDataObserver).onChanged(false)
        inOrder.verify(errorLiveDataObserver).onChanged(expectedException)
    }


    private fun getShoppingCartItems(): List<ShoppingCartItem> {
        return MockDataProvider.getBoxes(
            listOf(
                "X_MARVEL",
                "X_DC",
                "X_BUBBLECOMICS",
                "X_LUCASFILM"
            )
        ).map { box ->
            ShoppingCartItem.buildFromDTO(
                ShoppingCartItemDTO(box, true, 2, false)
            )
        }
    }
}