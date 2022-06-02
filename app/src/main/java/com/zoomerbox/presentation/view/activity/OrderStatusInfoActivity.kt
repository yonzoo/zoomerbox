package com.zoomerbox.presentation.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zoomerbox.R
import com.zoomerbox.databinding.ActivityOrderStatusInfoBinding

class OrderStatusInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderStatusInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderStatusInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.showAllOrders.setOnClickListener {
            startActivity(OrdersActivity.newIntent(this))
        }
        binding.toMain.setOnClickListener {
            startActivity(MainActivity.newIntent(this))
        }
    }

    companion object {
        val TAG: String = this::class.java.declaringClass.simpleName

        fun newIntent(context: Context): Intent {
            return Intent(context, OrderStatusInfoActivity::class.java)
        }
    }
}