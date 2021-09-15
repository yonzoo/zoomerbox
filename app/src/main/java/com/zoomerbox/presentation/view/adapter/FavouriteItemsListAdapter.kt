package com.zoomerbox.presentation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zoomerbox.R
import com.zoomerbox.databinding.ItemFavouriteBinding
import com.zoomerbox.model.item.ZoomerBox
import com.zoomerbox.presentation.view.activity.ZoomerBoxActivity

class FavouriteItemsListAdapter(
    private var favouriteItems: List<ZoomerBox>
) : RecyclerView.Adapter<FavouriteItemsListAdapter.FavouriteItemViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteItemViewHolder {
        context = parent.context
        return FavouriteItemViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_favourite, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavouriteItemViewHolder, position: Int) {
        holder.bind(favouriteItems[position])
    }

    override fun getItemCount(): Int {
        return favouriteItems.size
    }

    fun setData(favouriteItems: List<ZoomerBox>) {
        this.favouriteItems = favouriteItems
    }

    fun getData(): List<ZoomerBox> {
        return this.favouriteItems
    }

    inner class FavouriteItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var itemBinding: ItemFavouriteBinding = ItemFavouriteBinding.bind(itemView)

        fun bind(favouriteItem: ZoomerBox) {
            itemBinding.boxTitle.text = favouriteItem.name
            itemBinding.boxPrice.text = favouriteItem.price
            if (favouriteItem.imageUrls.isNotEmpty()) {
                Picasso.get().load(favouriteItem.imageUrls[0]).into(itemBinding.boxPreview)
            }
            itemBinding.boxWrapper.setOnClickListener {
                context.startActivity(ZoomerBoxActivity.newIntent(context, favouriteItem))
            }
            itemBinding.crossBtn.setOnClickListener {
            }
        }
    }
}