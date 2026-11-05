package com.styledrop.app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.styledrop.app.R
import com.styledrop.app.data.local.entity.WishlistItemEntity
import java.util.Locale

class WishlistAdapter(
    private var items: List<WishlistItemEntity>,
    private val onRemoveClick: (WishlistItemEntity) -> Unit
) : RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder>() {

    fun updateData(newItems: List<WishlistItemEntity>) {
        items = newItems
        notifyDataSetChanged()
    }

    class WishlistViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivWishlist: ImageView = view.findViewById(R.id.iv_wishlist)
        val tvTitle: TextView = view.findViewById(R.id.tv_wishlist_title)
        val tvBrand: TextView = view.findViewById(R.id.tv_wishlist_brand)
        val tvPrice: TextView = view.findViewById(R.id.tv_wishlist_price)
        val btnRemove: ImageButton = view.findViewById(R.id.btn_wishlist_remove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wishlist, parent, false)
        return WishlistViewHolder(view)
    }

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        val item = items[position]
        holder.tvTitle.text = item.title.ifBlank { item.name }
        holder.tvBrand.text = item.brand
        val zarPrice = item.price * 18.5
        holder.tvPrice.text = "R${"%,.2f".format(Locale.US, zarPrice)}"
        holder.ivWishlist.load(item.imageUrl) {
            crossfade(true)
        }

        holder.btnRemove.setOnClickListener { onRemoveClick(item) }
    }

    override fun getItemCount(): Int = items.size
}
