package com.styledrop.app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.styledrop.app.R
import com.styledrop.app.data.local.entity.OutfitEntity

class OutfitAdapter(
    private var outfits: List<OutfitEntity>
) : RecyclerView.Adapter<OutfitAdapter.OutfitViewHolder>() {

    fun updateData(newOutfits: List<OutfitEntity>) {
        outfits = newOutfits
        notifyDataSetChanged()
    }

    class OutfitViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_outfit_name)
        val tvSlots: TextView = view.findViewById(R.id.tv_outfit_slots)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OutfitViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_outfit, parent, false)
        return OutfitViewHolder(view)
    }

    override fun onBindViewHolder(holder: OutfitViewHolder, position: Int) {
        val outfit = outfits[position]
        holder.tvName.text = outfit.name
        holder.tvSlots.text = "Top ID: ${outfit.topId ?: "Empty"} | Bottom ID: ${outfit.bottomId ?: "Empty"} | Shoes ID: ${outfit.shoesId ?: "Empty"}"
    }

    override fun getItemCount(): Int = outfits.size
}
