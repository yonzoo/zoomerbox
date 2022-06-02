package com.zoomerbox.presentation.view.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zoomerbox.ZoomerboxApplication
import com.zoomerbox.databinding.ActivityOrdersBinding
import com.zoomerbox.di.activity.ActivityComponent
import com.zoomerbox.presentation.view.adapter.OrdersAdapter
import com.zoomerbox.presentation.viewmodel.OrdersViewModel
import com.zoomerbox.presentation.viewmodel.OrdersViewModelFactory
import javax.inject.Inject

/**
 * Экран со списком заказов
 */
class OrdersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrdersBinding
    private lateinit var viewModel: OrdersViewModel
    private val ordersAdapter = OrdersAdapter(emptyList())

    @Inject
    lateinit var viewModelFactory: OrdersViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ordersToMain.setOnClickListener {
            startActivity(MainActivity.newIntent(this))
        }
        binding.ordersList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.ordersList.adapter = ordersAdapter

        provideDependencies()
        createViewModel()
        setObservers()

        binding.noOrders.visibility = View.GONE
        binding.progressBar.visibility = View.GONE

        viewModel.loadOrders()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setObservers() {
        viewModel.getErrorLiveData().observe(this) {
            Toast.makeText(this, "Не получилось получить ответ от сервера", Toast.LENGTH_SHORT)
                .show()
            ordersAdapter.setData(listOf())
            ordersAdapter.notifyDataSetChanged()
            binding.noOrders.visibility = View.VISIBLE
        }
        viewModel.getOrdersLiveData().observe(this) { orders ->
            val ordersCopy = orders.toMutableList()
            ordersCopy.forEach { order ->
                order.totalOrderSum =
                    order.boxes.sumOf { orderBox -> orderBox.count * orderBox.box.price.toInt() }
            }
            ordersAdapter.setData(orders)
            ordersAdapter.notifyDataSetChanged()
            if (orders.isEmpty()) {
                binding.noOrders.visibility = View.VISIBLE
            } else {
                binding.noOrders.visibility = View.GONE
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
        viewModel = ViewModelProvider(this, viewModelFactory).get(OrdersViewModel::class.java)
    }

    private fun provideDependencies() {
        val activityComponent: ActivityComponent =
            ZoomerboxApplication.getAppComponent(this).getActivityComponent()
        activityComponent.inject(this)
    }

    companion object {
        val TAG: String = this::class.java.declaringClass.simpleName

        fun newIntent(context: Context): Intent {
            return Intent(context, OrdersActivity::class.java)
        }
    }
}