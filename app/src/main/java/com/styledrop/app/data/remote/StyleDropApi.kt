package com.styledrop.app.data.remote

import retrofit2.http.*

interface StyleDropApi {
    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): AuthResponse

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @GET("api/wardrobe")
    suspend fun getWardrobe(): List<WardrobeItemDto>

    @POST("api/wardrobe")
    suspend fun createWardrobe(@Body item: WardrobeItemDto)

    @PUT("api/wardrobe/{clientId}")
    suspend fun updateWardrobe(
        @Path("clientId") clientId: String,
        @Body item: WardrobeItemDto
    )

    @DELETE("api/wardrobe/{clientId}")
    suspend fun deleteWardrobe(@Path("clientId") clientId: String)

    @POST("api/sync")
    suspend fun sync(@Body request: SyncRequest): SyncResponse
}
