package com.zoomerbox.presentation.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zoomerbox.R
import com.zoomerbox.ZoomerboxApplication
import com.zoomerbox.databinding.FragmentShoppingCartBinding
import com.zoomerbox.di.fragment.FragmentComponent
import com.zoomerbox.model.app.OrderBox
import com.zoomerbox.presentation.view.activity.CreateOrderActivity
import com.zoomerbox.presentation.view.adapter.CartItemsListAdapter
import com.zoomerbox.presentation.viewmodel.ShoppingCartViewModel
import com.zoomerbox.presentation.viewmodel.ShoppingCartViewModelFactory
import javax.inject.Inject

class ShoppingCartFragment : Fragment() {

    private lateinit var binding: FragmentShoppingCartBinding
    private lateinit var viewModel: ShoppingCartViewModel
    private lateinit var cartItemsListAdapter: CartItemsListAdapter

    @Inject
    lateinit var viewModelFactory: ShoppingCartViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingCartBinding.inflate(layoutInflater)
        binding.doggo.visibility = View.GONE
        binding.cartItemsProgress.visibility = View.GONE

        binding.createOrderBtn.setOnClickListener {
            val selectedCartItems =
                cartItemsListAdapter.getData().filter { cartItem -> cartItem.selected }
            if (selectedCartItems.isNotEmpty()) {
                startActivity(
                    CreateOrderActivity.newIntent(
                        requireContext(),
                        ArrayList(selectedCartItems
                            .map { cartItem ->
                                OrderBox(
                                    cartItem.box,
                                    cartItem.count
                                )
                            })
                    )
                )
            }
        }

        provideDependencies()
        createViewModel()
        setObservers()

        cartItemsListAdapter = CartItemsListAdapter(emptyList(),
            onItemFavouriteToggled = {
                viewModel.toggleBoxFavourite(it)
            }, onItemDeleted = {
                viewModel.deleteShoppingCartItem(it)
            }, onItemAdded = {
                viewModel.addShoppingCartItem(it)
            }, onItemSelectToggled = {
                viewModel.toggleSelectShoppingCartItem(it)
            }, onSingleItemRemoved = {
                viewModel.removeSingleShoppingCartItem(it)
            })
        binding.cartItemsList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.cartItemsList.adapter = cartItemsListAdapter

        binding.removeSelected.setOnClickListener {
            viewModel.deleteAllShoppingCartItems(
                cartItemsListAdapter.getData().filter { item -> item.selected })
        }

        binding.selectAllArea.setOnClickListener {
            val items = cartItemsListAdapter.getData()
            if (items.filter { item -> !item.selected }.toList().isNotEmpty()) {
                viewModel.selectAllShoppingCartItems(items)
                binding.selectAllBtn.setImageResource(R.drawable.v1chosenicon)
            } else {
                viewModel.deselectAllShoppingCartItems(items)
                binding.selectAllBtn.setImageResource(R.drawable.v1notchosenicon)
            }
        }

        viewModel.loadShoppingCartItems()

        return binding.root
    }

    private fun provideDependencies() {
        val fragmentComponent: FragmentComponent =
            ZoomerboxApplication.getAppComponent(requireContext()).getFragmentComponent()
        fragmentComponent.inject(this)
    }

    private fun createViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(ShoppingCartViewModel::class.java)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setObservers() {
        viewModel.getCartItemsLiveData().observe(viewLifecycleOwner) { cartItems ->
            if (cartItems.isNotEmpty()) {
                binding.doggo.visibility = View.GONE
                if (cartItems.filter { item -> item.selected }
                        .toList().size == cartItems.size) {
                    binding.selectAllBtn.setImageResource(R.drawable.v1chosenicon)
                } else {
                    binding.selectAllBtn.setImageResource(R.drawable.v1notchosenicon)
                }
                val selectedItemsSum =
                    cartItems.filter { item -> item.selected }.map { item -> item.count }.sum()
                val selectedMoneySum =
                    cartItems.filter { item -> item.selected }
                        .map { item -> item.box.price.toInt() * item.count }
                        .sum()
                binding.totalSum.text = context?.resources?.getString(
                    R.string.money_amount,
                    selectedMoneySum.toString()
                )
                binding.totalItemAmount.text = context?.resources?.getQuantityString(
                    R.plurals.plurals,
                    selectedItemsSum,
                    selectedItemsSum
                )
                cartItemsListAdapter.setData(cartItems.sortedBy { it.box.name })
                cartItemsListAdapter.notifyDataSetChanged()
            } else {
                binding.selectAllBtn.setImageResource(R.drawable.v1notchosenicon)
                binding.totalItemAmount.text = context?.resources?.getQuantityString(
                    R.plurals.plurals,
                    0,
                    0
                )
                binding.doggo.visibility = View.VISIBLE
                binding.selectAllBtn.setImageResource(R.drawable.v1notchosenicon)
                binding.totalSum.text = context?.resources?.getString(
                    R.string.money_amount,
                    "0"
                )

                cartItemsListAdapter.setData(emptyList())
                cartItemsListAdapter.notifyDataSetChanged()
            }
        }
        viewModel.getProgressLiveData().observe(viewLifecycleOwner) { showProgress ->
            if (showProgress) {
                binding.cartItemsProgress.visibility = View.VISIBLE
            } else {
                binding.cartItemsProgress.visibility = View.GONE
            }
        }
        viewModel.getIsBoxFavouriteLiveData().observe(viewLifecycleOwner) { isFavourite ->
            val itemIndex = cartItemsListAdapter.getData().indexOf(
                cartItemsListAdapter.getData()
                    .find { item -> item.box.name == viewModel.updatedFavouriteItem?.box?.name }
            )
            val newData = cartItemsListAdapter.getData()
            if (newData.isEmpty()) {
                cartItemsListAdapter.setData(emptyList())
                cartItemsListAdapter.notifyDataSetChanged()
            } else {
                newData[itemIndex].isFavourite = isFavourite
                cartItemsListAdapter.setData(newData)
                cartItemsListAdapter.notifyItemChanged(itemIndex)
            }
        }
        viewModel.getCartItemsSelectedLiveData().observe(viewLifecycleOwner) { cartItems ->
            val items = cartItemsListAdapter.getData()
            if (items.filter { item -> !item.selected }.toList().isEmpty()) {
                binding.selectAllBtn.setImageResource(R.drawable.v1notchosenicon)
            }
            if (cartItems.filter { item -> item.selected }.toList().size == cartItems.size) {
                binding.selectAllBtn.setImageResource(R.drawable.v1chosenicon)
            }
            val selectedItemsSum =
                cartItems.filter { item -> item.selected }.map { item -> item.count }
                    .sum()
            val selectedMoneySum =
                cartItems.filter { item -> item.selected }
                    .map { item -> item.box.price.toInt() * item.count }
                    .sum()
            binding.totalSum.text =
                context?.resources?.getString(R.string.money_amount, selectedMoneySum.toString())
            binding.totalItemAmount.text = context?.resources?.getQuantityString(
                R.plurals.plurals,
                selectedItemsSum,
                selectedItemsSum
            )
            val itemIndex = items.indexOf(
                cartItemsListAdapter.getData()
                    .find { item -> item.box.name == viewModel.updatedSelectedItem?.box?.name }
            )
            cartItemsListAdapter.setData(cartItems.sortedBy { it.box.name })
            cartItemsListAdapter.notifyItemChanged(itemIndex)
        }
    }
}
