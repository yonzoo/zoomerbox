package com.zoomerbox.presentation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zoomerbox.R
import com.zoomerbox.databinding.ItemImageSwitcherBinding
import com.zoomerbox.model.app.SliderItem

class SliderAdapter(
    private var sliderItems: MutableList<SliderItem>
) : RecyclerView.Adapter<SliderAdapter.ViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_image_switcher, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sliderItems[position])
    }

    override fun getItemCount(): Int {
        return sliderItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var itemBinding: ItemImageSwitcherBinding = ItemImageSwitcherBinding.bind(itemView)

        fun bind(sliderItem: SliderItem) {
            Picasso.get().load(sliderItem.imageUrl).into(itemBinding.imageSlide)
            itemBinding.imageSlide.scaleType = ImageView.ScaleType.CENTER_CROP
        }
    }
}