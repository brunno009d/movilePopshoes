package com.example.movilepopshoes.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("preferencias-usuario")
class EstadoDataStore (private val context: Context){

    companion object {
        private val USUARIO_ID = intPreferencesKey("usuario_id")
        private val USUARIO_LOG = booleanPreferencesKey("usuario_log")
    }


    suspend fun guardarSession(usuario_id: Int, usuario_log: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[USUARIO_ID] = usuario_id
            preferences[USUARIO_LOG] = usuario_log
        }
    }

    // Obtener el id del usuario
    val usuario_id: Flow<Int?> = context.dataStore.data
        .map { preferences -> preferences[USUARIO_ID] }

    // Obtener el Estado del login
    val usuario_log: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[USUARIO_LOG] ?: false }

    suspend fun cerrarSession() {
        context.dataStore.edit { preferences -> preferences.clear() }
    }


}