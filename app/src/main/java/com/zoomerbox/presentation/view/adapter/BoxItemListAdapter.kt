package com.zoomerbox.presentation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zoomerbox.R
import com.zoomerbox.databinding.ItemBoxItemBinding
import com.zoomerbox.model.app.BoxItem
import com.zoomerbox.presentation.view.activity.BoxItemActivity

class BoxItemListAdapter(
    private var boxItemList: List<BoxItem>,
    private var boxName: String
) : RecyclerView.Adapter<BoxItemListAdapter.ViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_box_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(boxItemList[position])
    }

    override fun getItemCount(): Int {
        return boxItemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var itemBinding: ItemBoxItemBinding = ItemBoxItemBinding.bind(itemView)

        fun bind(boxItem: BoxItem) {
            itemBinding.boxItemTitle.text = boxItem.name
            if (boxItem.imageUrls.isNotEmpty()) {
                Picasso.get().load(boxItem.imageUrls[0]).into(itemBinding.boxItemPreview)
            }
            itemBinding.boxItem.setOnClickListener {
                context.startActivity(BoxItemActivity.newIntent(context, boxItem, boxName))
            }
        }
    }
}