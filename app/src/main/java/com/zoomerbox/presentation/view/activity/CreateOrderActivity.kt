package com.zoomerbox.presentation.view.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zoomerbox.ZoomerboxApplication
import com.zoomerbox.databinding.ActivityCreateOrderBinding
import com.zoomerbox.di.activity.ActivityComponent
import com.zoomerbox.model.item.OrderBox
import com.zoomerbox.presentation.view.adapter.OrderBoxesAdapter
import com.zoomerbox.presentation.view.util.BundleKeys
import com.zoomerbox.presentation.viewmodel.CreateOrderViewModel
import com.zoomerbox.presentation.viewmodel.CreateOrderViewModelFactory
import javax.inject.Inject

class CreateOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateOrderBinding
    private lateinit var viewModel: CreateOrderViewModel
    private val orderAdapter = OrderBoxesAdapter(emptyList())
    private lateinit var orderItems: List<OrderBox>

    @Inject
    lateinit var viewModelFactory: CreateOrderViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.createOrderItems.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.createOrderItems.adapter = orderAdapter

        orderItems = intent.getParcelableArrayListExtra(BundleKeys.ORDER_ITEMS)!!
        orderAdapter.setData(orderItems)
        orderAdapter.notifyDataSetChanged()

        provideDependencies()
        createViewModel()
        setObservers()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setObservers() {
        viewModel.getErrorLiveData().observe(this) {
            Toast.makeText(this, "Не получилось получить ответ от сервера", Toast.LENGTH_SHORT)
                .show()
            orderAdapter.setData(listOf())
            orderAdapter.notifyDataSetChanged()
        }
        viewModel.getOrderLiveData().observe(this) {
            finish()
        }
    }

    private fun createViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(CreateOrderViewModel::class.java)
    }

    private fun provideDependencies() {
        val activityComponent: ActivityComponent =
            ZoomerboxApplication.getAppComponent(this).getActivityComponent()
        activityComponent.inject(this)
    }

    companion object {
        val TAG: String = this::class.java.declaringClass.simpleName

        fun newIntent(context: Context, orderItems: ArrayList<OrderBox>): Intent {
            return Intent(context, CreateOrderActivity::class.java).apply {
                putParcelableArrayListExtra(BundleKeys.ORDER_ITEMS, orderItems)
            }
        }
    }
}