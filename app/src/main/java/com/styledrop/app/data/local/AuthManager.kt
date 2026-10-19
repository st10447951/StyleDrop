package com.styledrop.app.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.styledrop.app.data.remote.AuthResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")

class AuthManager(private val context: Context) {
    companion object {
        private val KEY_TOKEN = stringPreferencesKey("token")
        private val KEY_USER_ID = stringPreferencesKey("user_id")
        private val KEY_EMAIL = stringPreferencesKey("email")
        private val KEY_DISPLAY_NAME = stringPreferencesKey("display_name")
    }

    val authState: Flow<AuthResponse?> = context.dataStore.data.map { preferences ->
        val token = preferences[KEY_TOKEN]
        val userId = preferences[KEY_USER_ID]
        val email = preferences[KEY_EMAIL]
        val displayName = preferences[KEY_DISPLAY_NAME]

        if (token != null && userId != null && email != null && displayName != null) {
            AuthResponse(token, userId, email, displayName)
        } else {
            null
        }
    }

    suspend fun saveSession(authResponse: AuthResponse) {
        context.dataStore.edit { preferences ->
            preferences[KEY_TOKEN] = authResponse.token
            preferences[KEY_USER_ID] = authResponse.userId
            preferences[KEY_EMAIL] = authResponse.email
            preferences[KEY_DISPLAY_NAME] = authResponse.displayName
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
