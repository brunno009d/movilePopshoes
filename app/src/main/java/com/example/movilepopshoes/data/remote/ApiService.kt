package com.example.movilepopshoes.data.remote

import com.example.movilepopshoes.data.remote.model.Calzado
import com.example.movilepopshoes.data.remote.model.CompraRequest
import com.example.movilepopshoes.data.remote.model.Usuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import  okhttp3.ResponseBody

interface ApiService {

    @GET("api/calzados")
    suspend fun obtenerTodosLosCalzados(): Response<List<Calzado>>

    @GET("api/calzados/{id}")
    suspend fun obtenerCalzadoPorId(@Path("id") id: Int): Response<Calzado>

    // --- Usuario: Autenticación y Obtención ---
    @GET("api/usuarios/{id}")
    suspend fun obetenerUsuarioPorId(@Path("id") id: Int): Response<Usuario>

    @POST("api/usuarios")
    suspend fun registrarUsuario(@Body usuario: Usuario): Response<Usuario>

    @POST("api/usuarios/login")
    suspend fun login(@Body usuario: Usuario): Response<Usuario>

    @PATCH("api/usuarios/{id}")
    suspend fun actualizarDatosUsuario(
        @Path("id") id: Int,
        @Body usuario: Usuario
    ): Response<Usuario>

    @PATCH("api/usuarios/{id}/foto")
    suspend fun actualizarFotoPerfil(
        @Path("id") id: Int,
        @Body body: Map<String, String>
    ): Response<Any>

    @DELETE("api/usuarios/{id}")
    suspend fun eliminarUsuario(@Path("id") id: Int): Response<Void>

    @POST("api/compras")

    suspend fun crearCompra(@Body compra: CompraRequest): Response<ResponseBody>
}