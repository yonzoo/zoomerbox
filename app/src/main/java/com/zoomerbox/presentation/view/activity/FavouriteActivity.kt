package com.zoomerbox.presentation.view.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.zoomerbox.ZoomerboxApplication
import com.zoomerbox.databinding.ActivityFavouriteBinding
import com.zoomerbox.di.activity.ActivityComponent
import com.zoomerbox.presentation.view.adapter.FavouriteItemsListAdapter
import com.zoomerbox.presentation.viewmodel.FavouriteViewModel
import com.zoomerbox.presentation.viewmodel.FavouriteViewModelFactory
import javax.inject.Inject

class FavouriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavouriteBinding
    private lateinit var viewModel: FavouriteViewModel
    private val favouriteItemsListAdapter = FavouriteItemsListAdapter(emptyList())

    @Inject
    lateinit var viewModelFactory: FavouriteViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.favouriteItemsList.layoutManager = GridLayoutManager(this, 2)
        binding.favouriteItemsList.adapter = favouriteItemsListAdapter

        provideDependencies()
        createViewModel()
        setObservers()

        binding.noFavouriteItems.visibility = View.GONE
        binding.progressBar.visibility = View.GONE

        viewModel.loadFavouriteItems()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setObservers() {
        viewModel.getErrorLiveData().observe(this) {
            Toast.makeText(this, "Не получилось получить ответ от сервера", Toast.LENGTH_SHORT)
                .show()
            favouriteItemsListAdapter.setData(listOf())
            favouriteItemsListAdapter.notifyDataSetChanged()
            binding.noFavouriteItems.visibility = View.VISIBLE
        }
        viewModel.getFavouriteItemsLiveData().observe(this) { favouriteItems ->
            favouriteItemsListAdapter.setData(favouriteItems)
            favouriteItemsListAdapter.notifyDataSetChanged()
            if (favouriteItems.isEmpty()) {
                binding.noFavouriteItems.visibility = View.VISIBLE
            } else {
                binding.noFavouriteItems.visibility = View.GONE
            }
        }
        viewModel.getProgressLiveData().observe(this) { showProgress ->
            if (showProgress) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun createViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(FavouriteViewModel::class.java)
    }

    private fun provideDependencies() {
        val activityComponent: ActivityComponent =
            ZoomerboxApplication.getAppComponent(this).getActivityComponent()
        activityComponent.inject(this)
    }

    companion object {
        val TAG: String = this::class.java.declaringClass.simpleName

        fun newIntent(context: Context): Intent {
            return Intent(context, FavouriteActivity::class.java)
        }
    }
}