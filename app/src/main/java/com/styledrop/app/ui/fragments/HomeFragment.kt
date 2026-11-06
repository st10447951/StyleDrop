package com.styledrop.app.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.styledrop.app.R
import com.styledrop.app.data.local.StyleDropDatabase
import com.styledrop.app.data.local.entity.WishlistItemEntity
import com.styledrop.app.data.remote.Channel3FashionClient
import com.styledrop.app.data.remote.Channel3Product
import com.styledrop.app.ui.adapters.ProductAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private val products = mutableListOf<Channel3Product>()
    private val wishlistedIds = mutableSetOf<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycler_view_home)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        productAdapter = ProductAdapter(products, wishlistedIds) { product ->
            product.id?.let {
                toggleWishlist(product)
            }
        }
        recyclerView.adapter = productAdapter

        val db = StyleDropDatabase.getDatabase(requireContext())
        viewLifecycleOwner.lifecycleScope.launch {
            db.wishlistDao().getAllWishlistItems().collectLatest { list ->
                wishlistedIds.clear()
                wishlistedIds.addAll(list.map { it.productId })
                productAdapter.notifyDataSetChanged()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            val fetched = Channel3FashionClient.search("fashion")
            products.clear()
            products.addAll(fetched)
            productAdapter.notifyDataSetChanged()
        }
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
