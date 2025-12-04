package com.example.movilepopshoes.data.remote

interface ApiService {
    // --- CALZADOS ---
    @GET("api/calzados")
    suspend fun obtenerCalzados(): Response<List<Calzado>>

    // --- USUARIOS ---
    @POST("api/usuarios/login")
    suspend fun login(@Body usuario: Usuario): Response<Usuario>

    @POST("api/usuarios")
    suspend fun registrarUsuario(@Body usuario: Usuario): Response<Usuario>
}