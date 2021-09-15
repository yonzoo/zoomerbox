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
import com.zoomerbox.databinding.FragmentShopBinding
import com.zoomerbox.di.fragment.FragmentComponent
import com.zoomerbox.model.item.IShopListItem
import com.zoomerbox.presentation.view.adapter.CollectionsListAdapter
import com.zoomerbox.presentation.viewmodel.ShopViewModel
import com.zoomerbox.presentation.viewmodel.ShopViewModelFactory
import javax.inject.Inject

class ShopFragment : Fragment() {

    private lateinit var binding: FragmentShopBinding
    private lateinit var viewModel: ShopViewModel
    private val collectionsListAdapter = CollectionsListAdapter(emptyList())

    @Inject
    lateinit var viewModelFactory: ShopViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShopBinding.inflate(inflater, container, false)
        binding.collectionsList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.collectionsList.adapter = collectionsListAdapter

        provideDependencies()
        createViewModel()
        setObservers()

        viewModel.loadSeasonDrop()

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setObservers() {
        viewModel.getSeasonDropLiveData().observe(viewLifecycleOwner, { seasonDrop ->
            val newData: List<IShopListItem> =
                listOf(seasonDrop.banner, *seasonDrop.collections.toTypedArray())
            collectionsListAdapter.setData(newData)
            collectionsListAdapter.notifyDataSetChanged()
        })
        viewModel.getProgressLiveData().observe(viewLifecycleOwner, { showProgress ->
            if (showProgress) {
                binding.shopItemsProgress.visibility = View.VISIBLE
            } else {
                binding.shopItemsProgress.visibility = View.GONE
            }
        })
    }

    private fun createViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(ShopViewModel::class.java)
    }

    private fun provideDependencies() {
        val fragmentComponent: FragmentComponent =
            ZoomerboxApplication.getAppComponent(requireContext()).getFragmentComponent()
        fragmentComponent.inject(this)
    }
}
