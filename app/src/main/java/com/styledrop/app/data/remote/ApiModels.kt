package com.styledrop.app.data.remote

data class RegisterRequest(
    val email: String,
    val password: String,
    val displayName: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class AuthResponse(
    val token: String,
    val userId: String,
    val email: String,
    val displayName: String
)

data class WardrobeItemDto(
    val clientId: String,
    val name: String,
    val category: String,
    val brand: String,
    val colour: String,
    val size: String,
    val imageUrl: String?,
    val notes: String,
    val favourite: Boolean,
    val updatedAt: String,
    val isDeleted: Boolean
)

data class OutfitDto(
    val clientId: String,
    val name: String,
    val topId: String?,
    val bottomId: String?,
    val shoesId: String?,
    val outerwearId: String?,
    val accessoryId: String?,
    val favourite: Boolean,
    val updatedAt: String,
    val isDeleted: Boolean
)

data class SyncRequest(
    val wardrobe: List<WardrobeItemDto>,
    val outfits: List<OutfitDto>
)

data class SyncResponse(
    val wardrobe: List<WardrobeItemDto>,
    val outfits: List<OutfitDto>,
    val serverTime: String
)
