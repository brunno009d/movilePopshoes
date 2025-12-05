package com.example.movilepopshoes.data.remote.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.movilepopshoes.data.remote.ApiClient
import com.example.movilepopshoes.data.remote.model.Usuario
import com.example.movilepopshoes.utils.uriToMultipart

class UserRepository {

    private val api = ApiClient.service

    private val IMGBB_API_KEY = "226ad2357a913c124aec6c2eb0b23e1b"

    suspend fun subirImagenImgBB(context: Context, imageUri: Uri): String? {
        return try {
            val multipartImage = uriToMultipart(context, imageUri)

            val response = api.subirImagenImgBB(
                image = multipartImage,
                apiKey = IMGBB_API_KEY
            )

            if (response.isSuccessful && response.body() != null) {
                val url = response.body()!!.data.url
                Log.d("API_REPO", "Foto subida a ImgBB: $url")
                url // Retornamos la URL
            } else {
                Log.e("API_REPO", "Error ImgBB: ${response.errorBody()?.string()}")
                null
            }
        } catch (e: Exception) {
            Log.e("API_REPO", "Excepción subir foto: ${e.message}")
            null
        }
    }


    suspend fun registrarUsuario(usuario: Usuario): Boolean {
        return try {
            val response = api.registrarUsuario(usuario)
            response.isSuccessful
        } catch (e: Exception) { false }
    }

    suspend fun login(correo: String, clave: String): Usuario? {
        return try {
            val u = Usuario(nombre="", correo=correo, clave=clave)
            val response = api.login(u)
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) { null }
    }

    suspend fun obtenerUsuarioPorId(id: Int): Usuario? {
        return try {
            val response = api.obetenerUsuarioPorId(id)
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) { null }
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
            response.isSuccessful
        } catch (e: Exception) { false }
    }
}