package com.example.movilepopshoes.data.remote.repository

import android.util.Log
import com.example.movilepopshoes.data.remote.ApiClient
import com.example.movilepopshoes.data.remote.model.Usuario

class UserRepository{

    private val api = ApiClient.service

    suspend fun registrarUsuario(usuario: Usuario): Boolean {
        return try {
            val response = api.registrarUsuario(usuario)
            if (response.isSuccessful) {
                Log.d("API_USER", "Registro exitoso: ${response.body()}")
                true
            } else {
                Log.e("API_USER", "Error registro: ${response.code()} - ${response.errorBody()?.string()}")
                false
            }
        } catch (e: Exception) {
            Log.e("API_USER", "Fallo conexión registro: ${e.message}")
            false
        }
    }

    suspend fun login(correo: String, clave: String): Usuario? {
        return try {
            // Creamos un usuario temporal solo con correo y clave para enviar al backend
            val usuarioLogin = Usuario(
                nombre = "",
                correo = correo,
                clave = clave
            )

            val response = api.login(usuarioLogin)

            if (response.isSuccessful) {
                Log.d("API_USER", "Login exitoso: ${response.body()}")
                response.body()
            } else {
                Log.e("API_USER", "Error login: ${response.code()}")
                null
            }
        } catch (e: Exception) {
            Log.e("API_USER", "Fallo conexión login: ${e.message}")
            null
        }
    }

    suspend fun obtenerUsuarioPorId(id: Int): Usuario? {
        return try {
            val response = api.obetenerUsuarioPorId(id)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("API_USER", "Error obteniendo usuario $id: ${response.code()}")
                null
            }
        } catch (e: Exception) {
            Log.e("API_USER", "Fallo conexión obtener usuario: ${e.message}")
            null
        }
    }
}