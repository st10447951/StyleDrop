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
import com.styledrop.app.data.remote.Channel3Product

class ProductAdapter(
    private var products: List<Channel3Product>,
    private val wishlistedIds: Set<String>,
    private val onWishlistClick: (Channel3Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    fun updateData(newProducts: List<Channel3Product>) {
        products = newProducts
        notifyDataSetChanged()
    }

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivProduct: ImageView = view.findViewById(R.id.iv_product)
        val tvTitle: TextView = view.findViewById(R.id.tv_title)
        val tvBrand: TextView = view.findViewById(R.id.tv_brand)
        val tvPrice: TextView = view.findViewById(R.id.tv_price)
        val btnWishlist: ImageButton = view.findViewById(R.id.btn_wishlist)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.tvTitle.text = product.displayTitle
        holder.tvBrand.text = product.displayBrand
        holder.tvPrice.text = product.displayPriceRand
        holder.ivProduct.load(product.imageUrl) {
            crossfade(true)
        }

        val isWishlisted = product.id != null && product.id in wishlistedIds
        holder.btnWishlist.setImageResource(
            if (isWishlisted) android.R.drawable.star_on else android.R.drawable.star_off
        )

        holder.btnWishlist.setOnClickListener {
            onWishlistClick(product)
        }
    }

    override fun getItemCount(): Int = products.size
}
