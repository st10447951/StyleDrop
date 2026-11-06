package com.styledrop.app.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import coil.load
import com.styledrop.app.R
import com.styledrop.app.data.local.StyleDropDatabase
import com.styledrop.app.data.local.entity.WardrobeItemEntity
import com.styledrop.app.data.local.entity.WishlistItemEntity
import com.styledrop.app.data.remote.Channel3FashionClient
import com.styledrop.app.data.remote.Channel3Product
import com.styledrop.app.ui.adapters.ProductAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var tvEmpty: TextView
    private val products = mutableListOf<Channel3Product>()
    private val wishlistedIds = mutableSetOf<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycler_view_home)
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_home)
        tvEmpty = view.findViewById(R.id.tv_empty_home)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        productAdapter = ProductAdapter(
            wishlistedIds = wishlistedIds,
            onWishlistClick = { product ->
                product.id?.let {
                    toggleWishlist(product)
                }
            },
            onItemClick = { product ->
                showProductDetails(product)
            }
        )
        recyclerView.adapter = productAdapter

        swipeRefreshLayout.setOnRefreshListener {
            loadProducts()
        }

        val db = StyleDropDatabase.getDatabase(requireContext())
        viewLifecycleOwner.lifecycleScope.launch {
            db.wishlistDao().getAllWishlistItems().collectLatest { list ->
                wishlistedIds.clear()
                wishlistedIds.addAll(list.map { it.productId })
                productAdapter.notifyDataSetChanged()
            }
        }

        loadProducts()
    }

    private fun loadProducts() {
        swipeRefreshLayout.isRefreshing = true
        viewLifecycleOwner.lifecycleScope.launch {
            val fetched = Channel3FashionClient.search("fashion")
            products.clear()
            products.addAll(fetched)
            productAdapter.submitList(products.toList())
            swipeRefreshLayout.isRefreshing = false
            updateEmptyState()
        }
    }

    private fun updateEmptyState() {
        if (products.isEmpty()) {
            tvEmpty.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            tvEmpty.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    private fun showProductDetails(product: Channel3Product) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_product_detail, null)
        val ivImage = dialogView.findViewById<ImageView>(R.id.iv_detail_image)
        val tvTitle = dialogView.findViewById<TextView>(R.id.tv_detail_title)
        val tvBrand = dialogView.findViewById<TextView>(R.id.tv_detail_brand)
        val tvPrice = dialogView.findViewById<TextView>(R.id.tv_detail_price)
        val tvSizes = dialogView.findViewById<TextView>(R.id.tv_detail_sizes)

        ivImage.load(product.imageUrl) {
            crossfade(true)
        }
        tvTitle.text = product.displayTitle
        tvBrand.text = "Brand: ${product.displayBrand}"
        tvPrice.text = "Price: ${product.displayPriceRand}"
        tvSizes.text = "Available Sizes: ${product.sizes?.joinToString(", ") ?: "Standard Size"}"

        AlertDialog.Builder(requireContext())
            .setTitle("Product Details")
            .setView(dialogView)
            .setPositiveButton("Add to Wardrobe") { _, _ ->
                val db = StyleDropDatabase.getDatabase(requireContext())
                viewLifecycleOwner.lifecycleScope.launch {
                    db.wardrobeDao().insertItem(
                        WardrobeItemEntity(
                            name = product.displayTitle,
                            category = "Apparel",
                            brand = product.displayBrand,
                            colour = "Default",
                            size = product.sizes?.firstOrNull() ?: "M"
                        )
                    )
                }
            }
            .setNegativeButton("Close", null)
            .show()
    }

    private fun toggleWishlist(product: Channel3Product) {
        val db = StyleDropDatabase.getDatabase(requireContext())
        val pid = product.id ?: return
        val isWishlisted = pid in wishlistedIds
        viewLifecycleOwner.lifecycleScope.launch {
            if (isWishlisted) {
                db.wishlistDao().deleteWishlistItem(
                    WishlistItemEntity(
                        productId = pid,
                        name = product.name ?: "",
                        title = product.title ?: "",
                        brand = product.brand ?: "",
                        price = product.price ?: 0.0,
                        imageUrl = product.imageUrl
                    )
                )
            } else {
                db.wishlistDao().insertWishlistItem(
                    WishlistItemEntity(
                        productId = pid,
                        name = product.name ?: "",
                        title = product.title ?: "",
                        brand = product.brand ?: "",
                        price = product.price ?: 0.0,
                        imageUrl = product.imageUrl
                    )
                )
            }
        }
    }
}
