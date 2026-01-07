package com.uhc.api.chatbot

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "chatbot_settings")

/**
 * DataStore for chatbot settings.
 *
 * Note: DataStore implementation ideally belongs in a centralised `libraries` module
 * so it can be shared across features. To keep this project simple and because
 * the API key is only used here, the DataStore is kept in the `data`/`api-chatbot` layer for now.
 */
class ChatbotDataStore(
    private val context: Context
) {
    private val apiKey = stringPreferencesKey("api_key")

    val getApiKey: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[apiKey]
        }

    suspend fun saveApiKey(key: String) {
        context.dataStore.edit { preferences ->
            preferences[apiKey] = key
        }
    }
}
