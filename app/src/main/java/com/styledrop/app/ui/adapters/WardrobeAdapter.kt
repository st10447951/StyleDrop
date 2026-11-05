package com.styledrop.app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.styledrop.app.R
import com.styledrop.app.data.local.entity.WardrobeItemEntity

class WardrobeAdapter(
    private var items: List<WardrobeItemEntity>,
    private val onFavouriteClick: (WardrobeItemEntity) -> Unit,
    private val onDeleteClick: (WardrobeItemEntity) -> Unit
) : RecyclerView.Adapter<WardrobeAdapter.WardrobeViewHolder>() {

    fun updateData(newItems: List<WardrobeItemEntity>) {
        items = newItems
        notifyDataSetChanged()
    }

    class WardrobeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_wardrobe_name)
        val tvDetails: TextView = view.findViewById(R.id.tv_wardrobe_details)
        val tvMeta: TextView = view.findViewById(R.id.tv_wardrobe_meta)
        val btnFavourite: ImageButton = view.findViewById(R.id.btn_wardrobe_favourite)
        val btnDelete: ImageButton = view.findViewById(R.id.btn_wardrobe_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WardrobeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wardrobe, parent, false)
        return WardrobeViewHolder(view)
    }

    override fun onBindViewHolder(holder: WardrobeViewHolder, position: Int) {
        val item = items[position]
        holder.tvName.text = item.name
        holder.tvDetails.text = "${item.brand} • ${item.category}"
        holder.tvMeta.text = "${item.colour} • ${item.size}"

        holder.btnFavourite.setImageResource(
            if (item.favourite) android.R.drawable.star_on else android.R.drawable.star_off
        )

        holder.btnFavourite.setOnClickListener { onFavouriteClick(item) }
        holder.btnDelete.setOnClickListener { onDeleteClick(item) }
    }

    override fun getItemCount(): Int = items.size
}
