package com.example.movilepopshoes.data.remote.repository

import android.util.Log
import com.example.movilepopshoes.data.remote.ApiClient
import com.example.movilepopshoes.data.remote.model.Usuario

class UserRepository {

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
            Log.e("API_USER", "Fallo conexion login: ${e.message}")
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
            Log.e("API_USER", "Fallo conexion obtener usuario: ${e.message}")
            null
        }
    }

    suspend fun subirFotoPerfil(id: Int, imagenBase64: String): Boolean {
        return try {
            val body = mapOf("imagenUsuario" to imagenBase64)
            val response = api.actualizarFotoPerfil(id, body)
            if (response.isSuccessful) true else false
        } catch (e: Exception) { false }
    }

    suspend fun actualizarDatos(id: Int, usuario: Usuario): Boolean {
        return try {
            val response = api.actualizarDatosUsuario(id, usuario)
            if (response.isSuccessful) {
                Log.d("API_USER", "Datos actualizados correctamente")
                true
            } else {
                Log.e("API_USER", "Error al actualizar: ${response.code()}")
                false
            }
        } catch (e: Exception) {
            Log.e("API_USER", "Excepción al actualizar: ${e.message}")
            false
        }
    }
    suspend fun eliminarCuenta(id: Int): Boolean {
        return try {
            val response = api.eliminarUsuario(id)
            if (response.isSuccessful) {
                Log.d("API_USER", "Usuario eliminado correctamente")
                true
            } else {
                Log.e("API_USER", "Error al eliminar usuario: ${response.code()}")
                false
            }
        } catch (e: Exception) {
            Log.e("API_USER", "Excepción al eliminar: ${e.message}")
            false
        }
    }
}