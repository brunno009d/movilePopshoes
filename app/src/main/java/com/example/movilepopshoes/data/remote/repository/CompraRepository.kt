package com.example.movilepopshoes.data.remote.repository

import android.util.Log
import com.example.movilepopshoes.data.remote.ApiClient
import com.example.movilepopshoes.data.remote.model.CompraRequest

class CompraRepository {
    private val api = ApiClient.service

    suspend fun realizarCompra(compra: CompraRequest): Boolean {
        return try {
            val response = api.crearCompra(compra)
            if (response.isSuccessful) {
                Log.d("API_COMPRA", "Compra guardada con exito. Código: ${response.code()}")
                true
            } else {
                val errorString = response.errorBody()?.string()
                Log.e("API_COMPRA", "Error backend: ${response.code()} - $errorString")
                false
            }
        } catch (e: Exception) {
            Log.e("API_COMPRA", "Fallo conexion compra: ${e.message}")
            e.printStackTrace()
            false
        }
    }
}