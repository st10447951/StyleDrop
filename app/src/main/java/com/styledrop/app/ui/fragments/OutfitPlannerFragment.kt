package com.styledrop.app.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.styledrop.app.R
import com.styledrop.app.data.local.StyleDropDatabase
import com.styledrop.app.data.local.entity.OutfitEntity
import com.styledrop.app.data.local.entity.WishlistItemEntity
import com.styledrop.app.data.remote.Channel3FashionClient
import com.styledrop.app.data.remote.Channel3Product
import com.styledrop.app.ui.adapters.OutfitAdapter
import com.styledrop.app.ui.adapters.ProductAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class OutfitPlannerFragment : Fragment(R.layout.fragment_outfit_planner) {

    private lateinit var btnSlotTop: Button
    private lateinit var btnSlotBottom: Button
    private lateinit var btnSlotShoes: Button
    private lateinit var btnSlotOuterwear: Button
    private lateinit var btnSlotAccessory: Button

    private lateinit var tvActiveSlotTitle: TextView
    private lateinit var etSearchOutfit: TextInputEditText
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerViewSearch: RecyclerView
    private lateinit var etOutfitName: TextInputEditText
    private lateinit var btnSaveOutfit: Button
    private lateinit var tvSavedEmpty: TextView
    private lateinit var recyclerViewSaved: RecyclerView

    private lateinit var productAdapter: ProductAdapter
    private lateinit var outfitAdapter: OutfitAdapter

    private var activeSlot = "Top"
    private var selectedTop: Channel3Product? = null
    private var selectedBottom: Channel3Product? = null
    private var selectedShoes: Channel3Product? = null
    private var selectedOuterwear: Channel3Product? = null
    private var selectedAccessory: Channel3Product? = null

    private val searchResults = mutableListOf<Channel3Product>()
    private val savedOutfits = mutableListOf<OutfitEntity>()
    private val wishlistedIds = mutableSetOf<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSlotTop = view.findViewById(R.id.btn_slot_top)
        btnSlotBottom = view.findViewById(R.id.btn_slot_bottom)
        btnSlotShoes = view.findViewById(R.id.btn_slot_shoes)
        btnSlotOuterwear = view.findViewById(R.id.btn_slot_outerwear)
        btnSlotAccessory = view.findViewById(R.id.btn_slot_accessory)

        tvActiveSlotTitle = view.findViewById(R.id.tv_active_slot_title)
        etSearchOutfit = view.findViewById(R.id.et_search_outfit)
        progressBar = view.findViewById(R.id.progress_outfit_search)
        recyclerViewSearch = view.findViewById(R.id.recycler_view_outfit_search)
        etOutfitName = view.findViewById(R.id.et_outfit_name)
        btnSaveOutfit = view.findViewById(R.id.btn_save_outfit)
        tvSavedEmpty = view.findViewById(R.id.tv_saved_outfits_empty)
        recyclerViewSaved = view.findViewById(R.id.recycler_view_saved_outfits)

        recyclerViewSearch.layoutManager = LinearLayoutManager(requireContext())
        productAdapter = ProductAdapter(searchResults, wishlistedIds) { product ->
            when (activeSlot) {
                "Top" -> selectedTop = product
                "Bottom" -> selectedBottom = product
                "Shoes" -> selectedShoes = product
                "Outerwear" -> selectedOuterwear = product
                "Accessory" -> selectedAccessory = product
            }
            updateSlotButtons()
            etSearchOutfit.setText("")
        }
        recyclerViewSearch.adapter = productAdapter

        recyclerViewSaved.layoutManager = LinearLayoutManager(requireContext())
        outfitAdapter = OutfitAdapter(savedOutfits)
        recyclerViewSaved.adapter = outfitAdapter

        btnSlotTop.setOnClickListener { setActiveSlot("Top") }
        btnSlotBottom.setOnClickListener { setActiveSlot("Bottom") }
        btnSlotShoes.setOnClickListener { setActiveSlot("Shoes") }
        btnSlotOuterwear.setOnClickListener { setActiveSlot("Outerwear") }
        btnSlotAccessory.setOnClickListener { setActiveSlot("Accessory") }

        etSearchOutfit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                searchProducts(s?.toString() ?: "")
            }
        })

        btnSaveOutfit.setOnClickListener {
            val name = etOutfitName.text.toString().ifBlank { "Planned Look" }
            val db = StyleDropDatabase.getDatabase(requireContext())
            viewLifecycleOwner.lifecycleScope.launch {
                db.outfitDao().insertOutfit(
                    OutfitEntity(
                        name = name,
                        topId = selectedTop?.id?.hashCode(),
                        bottomId = selectedBottom?.id?.hashCode(),
                        shoesId = selectedShoes?.id?.hashCode(),
                        outerwearId = selectedOuterwear?.id?.hashCode(),
                        accessoryId = selectedAccessory?.id?.hashCode()
                    )
                )
                etOutfitName.setText("")
                selectedTop = null
                selectedBottom = null
                selectedShoes = null
                selectedOuterwear = null
                selectedAccessory = null
                updateSlotButtons()
            }
        }

        val db = StyleDropDatabase.getDatabase(requireContext())
        viewLifecycleOwner.lifecycleScope.launch {
            db.wishlistDao().getAllWishlistItems().collectLatest { list ->
                wishlistedIds.clear()
                wishlistedIds.addAll(list.map { it.productId })
                productAdapter.notifyDataSetChanged()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            db.outfitDao().getAllOutfits().collectLatest { list ->
                savedOutfits.clear()
                savedOutfits.addAll(list)
                outfitAdapter.notifyDataSetChanged()
                if (list.isEmpty()) {
                    tvSavedEmpty.visibility = View.VISIBLE
                    recyclerViewSaved.visibility = View.GONE
                } else {
                    tvSavedEmpty.visibility = View.GONE
                    recyclerViewSaved.visibility = View.VISIBLE
                }
            }
        }

        setActiveSlot("Top")
    }

    private fun setActiveSlot(slot: String) {
        activeSlot = slot
        tvActiveSlotTitle.text = "Search & Add items for $slot Slot"
        searchProducts(etSearchOutfit.text?.toString() ?: "")
    }

    private fun updateSlotButtons() {
        btnSlotTop.text = "Top slot: ${selectedTop?.displayTitle ?: "(Tap to select item)"}"
        btnSlotBottom.text = "Bottom slot: ${selectedBottom?.displayTitle ?: "(Tap to select item)"}"
        btnSlotShoes.text = "Shoes slot: ${selectedShoes?.displayTitle ?: "(Tap to select item)"}"
        btnSlotOuterwear.text = "Outerwear slot: ${selectedOuterwear?.displayTitle ?: "(Tap to select item)"}"
        btnSlotAccessory.text = "Accessory slot: ${selectedAccessory?.displayTitle ?: "(Tap to select item)"}"

        btnSaveOutfit.isEnabled = selectedTop != null || selectedBottom != null || selectedShoes != null
    }

    private fun searchProducts(query: String) {
        progressBar.visibility = View.VISIBLE
        viewLifecycleOwner.lifecycleScope.launch {
            val q = query.ifBlank { activeSlot }
            val results = Channel3FashionClient.search(q)
            searchResults.clear()
            searchResults.addAll(results)
            productAdapter.notifyDataSetChanged()
            progressBar.visibility = View.GONE
        }
    }
}
