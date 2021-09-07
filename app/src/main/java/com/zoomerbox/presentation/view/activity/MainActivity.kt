package com.zoomerbox.presentation.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.zoomerbox.R
import com.zoomerbox.databinding.ActivityMainBinding
import com.zoomerbox.presentation.view.fragment.ShopFragment
import com.zoomerbox.presentation.view.fragment.ShoppingCartFragment
import com.zoomerbox.presentation.view.fragment.UserFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val shopFragment = ShopFragment()
        val shoppingCartFragment = ShoppingCartFragment()
        val userFragment = UserFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainWrapper, shopFragment)
            addToBackStack(null)
            commit()
        }
        setActiveIcon(binding.shopIcon)
        setListeners(shopFragment, shoppingCartFragment, userFragment)
    }

    private fun setListeners(shopFragment: ShopFragment,
                             shoppingCartFragment: ShoppingCartFragment,
                             userFragment: UserFragment) {
        binding.shopIcon.setOnClickListener {
            setActiveFragment(shopFragment)
            setActiveIcon(binding.shopIcon)
        }
        binding.trolleyIcon.setOnClickListener {
            setActiveFragment(shoppingCartFragment)
            setActiveIcon(binding.trolleyIcon)
        }
        binding.userIcon.setOnClickListener {
            setActiveFragment(userFragment)
            setActiveIcon(binding.userIcon)
        }
    }

    private fun setActiveFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            when (fragment) {
                is ShopFragment -> {
                    setCustomAnimations(
                        R.anim.fade_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                    )
                }
                is UserFragment -> {
                    setCustomAnimations(
                        R.anim.slide_in_right,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                    )
                }
                else -> {
                    setCustomAnimations(
                        R.anim.slide_in_left,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                    )
                }
            }
            replace(R.id.mainWrapper, fragment)
            addToBackStack(null)
            commit()
        }
    }

    private fun setActiveIcon(view: ImageView) {
        val allIcons = listOf(binding.shopIcon, binding.trolleyIcon, binding.userIcon)
        allIcons.forEach { icon ->
            run {
                if (icon == view) {
                    icon.animate().scaleX(1.3F).scaleY(1.3F).setDuration(200L).start()
                } else {
                    icon.animate().scaleX(1F).scaleY(1F).setDuration(200L).start()
                }
            }
        }
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}