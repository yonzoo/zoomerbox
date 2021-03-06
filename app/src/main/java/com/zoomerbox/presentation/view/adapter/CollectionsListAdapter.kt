package com.zoomerbox.presentation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zoomerbox.R
import com.zoomerbox.databinding.ItemBannerBinding
import com.zoomerbox.databinding.ItemCollectionsListBinding
import com.zoomerbox.model.util.enumeration.ShopListItemTypeEnum
import com.zoomerbox.model.app.Banner
import com.zoomerbox.model.app.Collection
import com.zoomerbox.model.app.IShopListItem

class CollectionsListAdapter(
    private var shopItemList: List<IShopListItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var context: Context

    override fun getItemViewType(position: Int): Int {
        return shopItemList[position].getType().ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return when (viewType) {
            ShopListItemTypeEnum.COLLECTION.ordinal -> CollectionViewHolder(
                LayoutInflater.from(context)
                    .inflate(R.layout.item_collections_list, parent, false)
            )
            ShopListItemTypeEnum.BANNER.ordinal -> BannerViewHolder(
                LayoutInflater.from(context)
                    .inflate(R.layout.item_banner, parent, false)
            )
            else -> throw IllegalStateException("Provided view type is not supported");
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (shopItemList[position].getType()) {
            ShopListItemTypeEnum.COLLECTION -> (holder as CollectionViewHolder).bind(shopItemList[position] as Collection)
            ShopListItemTypeEnum.BANNER -> (holder as BannerViewHolder).bind(shopItemList[position] as Banner)
        }
    }

    override fun getItemCount(): Int {
        return shopItemList.size
    }

    fun setData(shopItemList: List<IShopListItem>) {
        this.shopItemList = shopItemList
    }

    fun getData(): List<IShopListItem> {
        return shopItemList
    }

    inner class CollectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var itemBinding: ItemCollectionsListBinding =
            ItemCollectionsListBinding.bind(itemView)

        fun bind(collection: Collection) {
            itemBinding.collectionTitle.text = collection.collectionName
            itemBinding.collectionCollabBoxList.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            itemBinding.collectionCollabBoxList.adapter =
                ZoomerBoxListAdapter(collection.boxes.sortedBy { it.name })
        }
    }

    inner class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var itemBinding: ItemBannerBinding =
            ItemBannerBinding.bind(itemView)

        fun bind(banner: Banner) {
            if (banner.imageUrl.isNotEmpty()) {
                Picasso.get().load(banner.imageUrl).into(itemBinding.bannerImage)
            }
        }
    }
}
