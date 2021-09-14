package com.zoomerbox.presentation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zoomerbox.R
import com.zoomerbox.databinding.ItemCollabBoxBinding
import com.zoomerbox.model.item.ZoomerBox
import com.zoomerbox.presentation.view.activity.ZoomerBoxActivity

class ZoomerBoxListAdapter(
    private var zoomerBoxList: List<ZoomerBox>
) : RecyclerView.Adapter<ZoomerBoxListAdapter.ViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_collab_box, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(zoomerBoxList[position])
    }

    override fun getItemCount(): Int {
        return zoomerBoxList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var itemBinding: ItemCollabBoxBinding = ItemCollabBoxBinding.bind(itemView)

        fun bind(zoomerBox: ZoomerBox) {
            itemBinding.boxTitle.text = zoomerBox.name
            itemBinding.boxPrice.text = zoomerBox.price
            if (zoomerBox.imageUrls.isNotEmpty()) {
                Picasso.get().load(zoomerBox.imageUrls[0]).into(itemBinding.boxPreview)
            }
            itemBinding.collectionItem.setOnClickListener {
                context.startActivity(ZoomerBoxActivity.newIntent(context, zoomerBox))
            }
        }
    }
}