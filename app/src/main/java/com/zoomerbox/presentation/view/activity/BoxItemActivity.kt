package com.zoomerbox.presentation.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.zoomerbox.R
import com.zoomerbox.databinding.ActivityBoxItemBinding
import com.zoomerbox.model.app.BoxItem
import com.zoomerbox.model.app.SliderItem
import com.zoomerbox.presentation.view.adapter.SliderAdapter
import com.zoomerbox.presentation.view.util.BundleKeys

class BoxItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBoxItemBinding
    private lateinit var boxItem: BoxItem
    private lateinit var dots: MutableList<TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoxItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dots = mutableListOf()
        boxItem = intent.getParcelableExtra(BundleKeys.BOX_ITEM)!!
        val boxName = intent.getStringExtra(BundleKeys.PARENT_BOX)!!
        setIndicators()
        binding.boxItemName.text = boxItem.name
        binding.boxDescription.text = boxItem.description
        binding.boxReference.text = getString(R.string.item_is_included, boxName)
        binding.itemImageSwitcher.adapter =
            SliderAdapter(boxItem.imageUrls.map { imageUrl -> SliderItem(imageUrl) }
                .toMutableList())
        binding.itemImageSwitcher.clipToPadding = false
        binding.itemImageSwitcher.clipChildren = false
        binding.itemImageSwitcher.offscreenPageLimit = 3
        binding.itemImageSwitcher.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        binding.itemImageSwitcher.registerOnPageChangeCallback(object :
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
        for (i in boxItem.imageUrls.indices) {
            val textView = TextView(this)
            textView.text = Html.fromHtml("&#9679;")
            textView.textSize = 18f
            textView.setTextColor(resources.getColor(R.color.grey_200))
            dots.add(textView)
            binding.itemDotsContainer.addView(textView)
        }
    }

    companion object {
        fun newIntent(context: Context, boxItem: BoxItem, boxName: String): Intent {
            return Intent(context, BoxItemActivity::class.java).apply {
                putExtra(BundleKeys.BOX_ITEM, boxItem)
                putExtra(BundleKeys.PARENT_BOX, boxName)
            }
        }
    }
}
