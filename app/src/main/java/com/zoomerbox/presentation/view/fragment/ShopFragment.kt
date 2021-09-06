package com.zoomerbox.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.zoomerbox.ZoomerboxApplication
import com.zoomerbox.databinding.FragmentShopBinding
import com.zoomerbox.di.fragment.FragmentComponent
import com.zoomerbox.presentation.view.adapter.CollectionsListAdapter
import com.zoomerbox.presentation.view.adapter.CollectionsListDiffUtilCallback
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

        //TODO add swipe layout to fragment so the list could be nicely updated
        //TODO add lookup repository based on stage, for example if we are in 'mocking' stage, lookup all mock implementations instead of real ones

        viewModel.loadSeasonDrop()

        return binding.root
    }

    private fun setObservers() {
        viewModel.getSeasonDropLiveData().observe(viewLifecycleOwner, { seasonDrop ->
            val diffUtilCallback = CollectionsListDiffUtilCallback(
                collectionsListAdapter.getData(),
                seasonDrop.collections
            )
            val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
            collectionsListAdapter.setData(seasonDrop.collections)
            if (!binding.collectionsList.isComputingLayout) {
                diffResult.dispatchUpdatesTo(collectionsListAdapter)
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
