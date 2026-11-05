package com.styledrop.app.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.styledrop.app.R
import com.styledrop.app.data.local.StyleDropDatabase
import com.styledrop.app.data.local.entity.WishlistItemEntity
import com.styledrop.app.ui.adapters.WishlistAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WishlistFragment : Fragment(R.layout.fragment_wishlist) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tvEmpty: TextView
    private lateinit var wishlistAdapter: WishlistAdapter
    private val wishlistItems = mutableListOf<WishlistItemEntity>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycler_view_wishlist)
        tvEmpty = view.findViewById(R.id.tv_wishlist_empty)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        wishlistAdapter = WishlistAdapter(wishlistItems) { item ->
            val db = StyleDropDatabase.getDatabase(requireContext())
            viewLifecycleOwner.lifecycleScope.launch {
                db.wishlistDao().deleteWishlistItem(item)
            }
        }
        recyclerView.adapter = wishlistAdapter

        val db = StyleDropDatabase.getDatabase(requireContext())
        viewLifecycleOwner.lifecycleScope.launch {
            db.wishlistDao().getAllWishlistItems().collectLatest { list ->
                wishlistItems.clear()
                wishlistItems.addAll(list)
                wishlistAdapter.notifyDataSetChanged()
                if (list.isEmpty()) {
                    tvEmpty.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                } else {
                    tvEmpty.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }
            }
        }
    }
}
