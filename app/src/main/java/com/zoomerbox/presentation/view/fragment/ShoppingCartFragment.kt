package com.zoomerbox.presentation.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zoomerbox.ZoomerboxApplication
import com.zoomerbox.databinding.FragmentShoppingCartBinding
import com.zoomerbox.di.fragment.FragmentComponent
import com.zoomerbox.presentation.view.adapter.CartItemsListAdapter
import com.zoomerbox.presentation.viewmodel.ShoppingCartViewModel
import com.zoomerbox.presentation.viewmodel.ShoppingCartViewModelFactory
import javax.inject.Inject

class ShoppingCartFragment : Fragment() {

    private lateinit var binding: FragmentShoppingCartBinding
    private lateinit var viewModel: ShoppingCartViewModel
    private val cartItemsListAdapter = CartItemsListAdapter(emptyList())

    @Inject
    lateinit var viewModelFactory: ShoppingCartViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingCartBinding.inflate(layoutInflater)
        binding.cartItemsList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.cartItemsList.adapter = cartItemsListAdapter
        binding.doggo.visibility = View.GONE
        binding.cartItemsProgress.visibility = View.GONE

        provideDependencies()
        createViewModel()
        setObservers()

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
                cartItemsListAdapter.setData(cartItems)
                cartItemsListAdapter.notifyDataSetChanged()
            } else {
                binding.doggo.visibility = View.VISIBLE
            }
        }
        viewModel.getProgressLiveData().observe(viewLifecycleOwner) { showProgress ->
            if (showProgress) {
                binding.cartItemsProgress.visibility = View.VISIBLE
            } else {
                binding.cartItemsProgress.visibility = View.GONE
            }
        }
    }
}
