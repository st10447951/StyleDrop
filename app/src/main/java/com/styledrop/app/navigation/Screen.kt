package com.styledrop.app.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Home : Screen("home")
    data object Wardrobe : Screen("wardrobe")
    data object Outfits : Screen("outfits")
    data object Inspiration : Screen("inspiration")
    data object Wishlist : Screen("wishlist")
    data object Profile : Screen("profile")
}
