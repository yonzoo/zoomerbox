package com.zoomerbox.presentation.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.zoomerbox.R
import com.zoomerbox.ZoomerboxApplication
import com.zoomerbox.databinding.ActivityZoomerBoxBinding
import com.zoomerbox.di.activity.ActivityComponent
import com.zoomerbox.model.app.SliderItem
import com.zoomerbox.model.app.ZoomerBox
import com.zoomerbox.presentation.view.adapter.BoxItemListAdapter
import com.zoomerbox.presentation.view.adapter.SliderAdapter
import com.zoomerbox.presentation.view.util.BundleKeys
import com.zoomerbox.presentation.viewmodel.ZoomerBoxViewModel
import com.zoomerbox.presentation.viewmodel.ZoomerBoxViewModelFactory
import javax.inject.Inject

class ZoomerBoxActivity : AppCompatActivity() {

    private lateinit var binding: ActivityZoomerBoxBinding
    private lateinit var zoomerBox: ZoomerBox
    private lateinit var dots: MutableList<TextView>
    private lateinit var viewModel: ZoomerBoxViewModel

    @Inject
    lateinit var viewModelFactory: ZoomerBoxViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityZoomerBoxBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dots = mutableListOf()
        zoomerBox = intent.getParcelableExtra(BundleKeys.ZOOMER_BOX)!!

        provideDependencies()
        createViewModel()
        setObservers()
        setIndicators()

        binding.boxItems.adapter = BoxItemListAdapter(zoomerBox.items, zoomerBox.name)
        binding.boxItems.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.zoomerBoxTitle.text = zoomerBox.name
        binding.priceText.text = resources.getString(R.string.money_amount, zoomerBox.price)
        binding.boxDescription.text = zoomerBox.description
        binding.imageSwitcher.adapter =
            SliderAdapter(zoomerBox.imageUrls.map { imageUrl -> SliderItem(imageUrl) }
                .toMutableList())
        binding.imageSwitcher.clipToPadding = false
        binding.imageSwitcher.clipChildren = false
        binding.imageSwitcher.offscreenPageLimit = 3
        binding.imageSwitcher.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        binding.imageSwitcher.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                setActiveDot(position)
                super.onPageSelected(position)
            }
        })

        binding.fab.setOnClickListener {
            viewModel.toggleBoxFavourite(zoomerBox.name)
        }
        binding.addToCartBtn.setOnClickListener {
            viewModel.addToCart(zoomerBox)
        }

        viewModel.isBoxFavourite(zoomerBox.name)
    }

    private fun setObservers() {
        viewModel.getErrorLiveData().observe(this) {
            Toast.makeText(this, "Не получилось загрузить бокс", Toast.LENGTH_SHORT).show()
        }
        viewModel.getResultLiveData().observe(this) {
            Toast.makeText(this, "Бокс добавлен в корзину!", Toast.LENGTH_SHORT).show()
        }
        viewModel.getFavouriteLiveData().observe(this) { isFavourite ->
            if (isFavourite) {
                binding.fab.setImageResource(R.drawable.hearticon)
            } else {
                binding.fab.setImageResource(R.drawable.nonhearticon)
            }
        }
    }

    private fun createViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(ZoomerBoxViewModel::class.java)
    }

    private fun provideDependencies() {
        val activityComponent: ActivityComponent =
            ZoomerboxApplication.getAppComponent(this).getActivityComponent()
        activityComponent.inject(this)
    }

    private fun setActiveDot(position: Int) {
        for (i in dots.indices) {
            if (position == i) {
                dots[i].setTextColor(resources.getColor(R.color.purple_700))
            } else {
                dots[i].setTextColor(resources.getColor(R.color.grey_200))
            }
        }
    }

    private fun setIndicators() {
        for (i in zoomerBox.imageUrls.indices) {
            val textView = TextView(this)
            textView.text = Html.fromHtml("&#9679;")
            textView.textSize = 18f
            textView.setTextColor(resources.getColor(R.color.grey_200))
            dots.add(textView)
            binding.dotsContainer.addView(textView)
        }
    }

    companion object {
        fun newIntent(context: Context, zoomerBox: ZoomerBox): Intent {
            return Intent(context, ZoomerBoxActivity::class.java).apply {
                putExtra(BundleKeys.ZOOMER_BOX, zoomerBox)
            }
        }
    }
}