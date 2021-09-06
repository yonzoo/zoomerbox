package com.zoomerbox.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zoomerbox.R
import com.zoomerbox.databinding.ItemCollectionsListBinding
import com.zoomerbox.model.dto.CollectionDTO

class CollectionsListAdapter(
    private var collectionsList: List<CollectionDTO>
) : RecyclerView.Adapter<CollectionsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_collections_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(collectionsList[position])
    }

    override fun getItemCount(): Int {
        return collectionsList.size
    }

    fun setData(collectionsList: List<CollectionDTO>) {
        this.collectionsList = collectionsList
    }

    fun getData(): List<CollectionDTO> {
        return collectionsList
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var itemBinding: ItemCollectionsListBinding =
            ItemCollectionsListBinding.bind(itemView)

        fun bind(collection: CollectionDTO) {
            itemBinding.collectionTitle.text = collection.collectionName
            //TODO bind boxes
        }
    }
}
