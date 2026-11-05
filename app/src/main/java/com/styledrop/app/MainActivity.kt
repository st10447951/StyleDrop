package com.styledrop.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.styledrop.app.ui.fragments.HomeFragment
import com.styledrop.app.ui.fragments.OutfitPlannerFragment
import com.styledrop.app.ui.fragments.ProfileFragment
import com.styledrop.app.ui.fragments.WardrobeFragment
import com.styledrop.app.ui.fragments.WishlistFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, HomeFragment())
                .commit()
        }

        bottomNav.setOnItemSelectedListener { item ->
            val fragment: Fragment = when (item.itemId) {
                R.id.nav_home -> HomeFragment()
                R.id.nav_wardrobe -> WardrobeFragment()
                R.id.nav_outfits -> OutfitPlannerFragment()
                R.id.nav_wishlist -> WishlistFragment()
                R.id.nav_profile -> ProfileFragment()
                else -> HomeFragment()
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .commit()
            true
        }
    }
}
