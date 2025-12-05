package com.example.movilepopshoes.data.remote.repository

import android.util.Log
import androidx.annotation.DrawableRes
import com.example.movilepopshoes.R
import com.example.movilepopshoes.data.remote.ApiClient
import com.example.movilepopshoes.data.remote.model.Calzado
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class CalzadoRepository {

    private val api = ApiClient.service
    val allCalzados: Flow<List<Calzado>> = flow {
        try {
            val response = api.obtenerTodosLosCalzados()
            if (response.isSuccessful) {
                emit(response.body() ?: emptyList())
            } else {
                Log.e("API_CALZADO", "Error: ${response.code()}")
                emit(emptyList())
            }
        } catch (e: Exception) {
            Log.e("API_CALZADO", "Fallo conexión: ${e.message}")
            emit(emptyList())
        }
    }

    fun getCalzadoById(id: Int): Flow<Calzado?> = flow {
        try {
            val response = api.obtenerCalzadoPorId(id)
            if (response.isSuccessful) {
                emit(response.body())
            } else {
                emit(null)
            }
        } catch (e: Exception) {
            emit(null)
        }
    }

}

