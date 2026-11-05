package com.styledrop.app.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object FirebaseAuthHelper {
    private val auth: FirebaseAuth? by lazy {
        try {
            FirebaseAuth.getInstance()
        } catch (e: Exception) {
            null
        }
    }

    private val firestore: FirebaseFirestore? by lazy {
        try {
            FirebaseFirestore.getInstance()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun register(email: String, password: String, displayName: String): AuthResponse {
        val firebaseAuth = auth ?: throw Exception("Firebase Auth not initialized")
        val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        val user = authResult.user ?: throw Exception("Firebase registration failed")
        val userId = user.uid

        try {
            val userMap = mapOf(
                "userId" to userId,
                "email" to email,
                "displayName" to displayName
            )
            firestore?.collection("users")?.document(userId)?.set(userMap)?.await()
        } catch (_: Exception) {}

        val token = try {
            user.getIdToken(true).await().token ?: "firebase-token"
        } catch (e: Exception) {
            "firebase-token"
        }

        return AuthResponse(
            token = token,
            userId = userId,
            email = email,
            displayName = displayName
        )
    }

    suspend fun login(email: String, password: String): AuthResponse {
        val firebaseAuth = auth ?: throw Exception("Firebase Auth not initialized")
        val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        val user = authResult.user ?: throw Exception("Firebase login failed")
        val userId = user.uid

        var displayName = user.displayName ?: user.email?.substringBefore("@") ?: "Fashionista"
        try {
            val doc = firestore?.collection("users")?.document(userId)?.get()?.await()
            if (doc != null && doc.exists()) {
                displayName = doc.getString("displayName") ?: displayName
            }
        } catch (_: Exception) {}

        val token = try {
            user.getIdToken(true).await().token ?: "firebase-token"
        } catch (e: Exception) {
            "firebase-token"
        }

        return AuthResponse(
            token = token,
            userId = userId,
            email = email,
            displayName = displayName
        )
    }

    fun signOut() {
        try {
            auth?.signOut()
        } catch (_: Exception) {}
    }
}
