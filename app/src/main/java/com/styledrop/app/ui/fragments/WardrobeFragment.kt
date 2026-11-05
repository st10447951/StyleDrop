package com.styledrop.app.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.styledrop.app.R
import com.styledrop.app.data.local.StyleDropDatabase
import com.styledrop.app.data.local.entity.WardrobeItemEntity
import com.styledrop.app.ui.adapters.WardrobeAdapter
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WardrobeFragment : Fragment(R.layout.fragment_wardrobe) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tvEmpty: TextView
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var wardrobeAdapter: WardrobeAdapter
    private val wardrobeItems = mutableListOf<WardrobeItemEntity>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycler_view_wardrobe)
        tvEmpty = view.findViewById(R.id.tv_wardrobe_empty)
        fabAdd = view.findViewById(R.id.fab_add_wardrobe)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        wardrobeAdapter = WardrobeAdapter(
            wardrobeItems,
            onFavouriteClick = { item ->
                val db = StyleDropDatabase.getDatabase(requireContext())
                viewLifecycleOwner.lifecycleScope.launch {
                    db.wardrobeDao().updateItem(
                        item.copy(
                            favourite = !item.favourite,
                            updatedAt = System.currentTimeMillis(),
                            syncState = "PENDING"
                        )
                    )
                }
            },
            onDeleteClick = { item ->
                val db = StyleDropDatabase.getDatabase(requireContext())
                viewLifecycleOwner.lifecycleScope.launch {
                    db.wardrobeDao().updateItem(
                        item.copy(
                            isDeleted = true,
                            updatedAt = System.currentTimeMillis(),
                            syncState = "PENDING"
                        )
                    )
                }
            }
        )
        recyclerView.adapter = wardrobeAdapter

        fabAdd.setOnClickListener {
            showAddDialog()
        }

        val db = StyleDropDatabase.getDatabase(requireContext())
        viewLifecycleOwner.lifecycleScope.launch {
            db.wardrobeDao().getAllItems().collectLatest { list ->
                wardrobeItems.clear()
                wardrobeItems.addAll(list)
                wardrobeAdapter.notifyDataSetChanged()
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

    private fun showAddDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_wardrobe, null)
        val etName = dialogView.findViewById<TextInputEditText>(R.id.et_name)
        val etCategory = dialogView.findViewById<TextInputEditText>(R.id.et_category)
        val etBrand = dialogView.findViewById<TextInputEditText>(R.id.et_brand)
        val etColour = dialogView.findViewById<TextInputEditText>(R.id.et_colour)
        val etSize = dialogView.findViewById<TextInputEditText>(R.id.et_size)

        AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val name = etName.text.toString()
                if (name.isNotBlank()) {
                    val category = etCategory.text.toString().ifBlank { "T-Shirt" }
                    val brand = etBrand.text.toString()
                    val colour = etColour.text.toString()
                    val size = etSize.text.toString()

                    val db = StyleDropDatabase.getDatabase(requireContext())
                    viewLifecycleOwner.lifecycleScope.launch {
                        db.wardrobeDao().insertItem(
                            WardrobeItemEntity(
                                name = name,
                                category = category,
                                brand = brand,
                                colour = colour,
                                size = size
                            )
                        )
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
