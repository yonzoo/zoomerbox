package com.zoomerbox.presentation.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.zoomerbox.R
import com.zoomerbox.databinding.ActivityZoomerBoxBinding
import com.zoomerbox.model.item.SliderItem
import com.zoomerbox.model.item.ZoomerBox
import com.zoomerbox.presentation.view.adapter.BoxItemListAdapter
import com.zoomerbox.presentation.view.adapter.SliderAdapter
import com.zoomerbox.presentation.view.util.BundleKeys

class ZoomerBoxActivity : AppCompatActivity() {

    private lateinit var binding: ActivityZoomerBoxBinding
    private lateinit var zoomerBox: ZoomerBox
    private lateinit var dots: MutableList<TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityZoomerBoxBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dots = mutableListOf()
        zoomerBox = intent.getParcelableExtra(BundleKeys.ZOOMER_BOX)!!
        setIndicators()
        binding.boxItems.adapter = BoxItemListAdapter(zoomerBox.items, zoomerBox.name)
        binding.boxItems.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.zoomerBoxTitle.text = zoomerBox.name
        binding.priceText.text = zoomerBox.price
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