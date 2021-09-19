package com.zoomerbox.presentation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zoomerbox.R
import com.zoomerbox.databinding.ItemShoppingCartBinding
import com.zoomerbox.model.app.ShoppingCartItem

class CartItemsListAdapter(
    private var cartItems: List<ShoppingCartItem>
) : RecyclerView.Adapter<CartItemsListAdapter.CartItemViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        context = parent.context
        return CartItemViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_shopping_cart, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    fun setData(cartItemList: List<ShoppingCartItem>) {
        this.cartItems = cartItemList
    }

    fun getData(): List<ShoppingCartItem> {
        return this.cartItems
    }

    inner class CartItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var itemBinding: ItemShoppingCartBinding = ItemShoppingCartBinding.bind(itemView)

        fun bind(cartItem: ShoppingCartItem) {
            itemBinding.boxTitle.text = cartItem.box.name
            itemBinding.zoomerBoxPrice.text = cartItem.box.price
            itemBinding.itemAmount.text =
                context.resources.getString(R.string.item_amount, cartItem.count.toString())
            if (cartItem.selected) {
                itemBinding.selectBtn.setImageResource(R.drawable.v1chosenicon)
            } else {
                itemBinding.selectBtn.setImageResource(R.drawable.v1notchosenicon)
            }
            if (cartItem.isFavourite) {
                itemBinding.likeBtn.setImageResource(R.drawable.hearticon)
            } else {
                itemBinding.likeBtn.setImageResource(R.drawable.nonhearticon)
            }
            if (cartItem.box.imageUrls.isNotEmpty()) {
                Picasso.get().load(cartItem.box.imageUrls[0]).into(itemBinding.boxItemPreview)
            }
            itemBinding.selectBtn.setOnClickListener { TODO("Implement") }
            itemBinding.selectBtn.setOnClickListener { TODO("Implement") }
            itemBinding.likeArea.setOnClickListener { TODO("Implement") }
            itemBinding.trashArea.setOnClickListener { TODO("Implement") }
        }
    }
}
