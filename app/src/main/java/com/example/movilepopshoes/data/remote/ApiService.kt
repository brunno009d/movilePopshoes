package com.example.movilepopshoes.data.remote

import com.example.movilepopshoes.data.model.Calzado
import com.example.movilepopshoes.data.model.CompraRequest
import com.example.movilepopshoes.data.model.ImgBBResponse
import com.example.movilepopshoes.data.model.Usuario
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import  okhttp3.ResponseBody
import retrofit2.http.Multipart
import retrofit2.http.Part
import retrofit2.http.Query
import retrofit2.http.Url

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



    @DELETE("api/usuarios/{id}")
    suspend fun eliminarUsuario(@Path("id") id: Int): Response<Void>

    @POST("api/compras")
    suspend fun crearCompra(@Body compra: CompraRequest): Response<ResponseBody>

    @Multipart
    @POST
    suspend fun subirImagenImgBB(
        @Url url: String = "https://api.imgbb.com/1/upload",
        @Part image: MultipartBody.Part,
        @Query("key") apiKey: String
    ): Response<ImgBBResponse>

    @PATCH("api/usuarios/{id}/foto")
    suspend fun actualizarFotoPerfil(
        @Path("id") id: Int,
        @Body body: Map<String, String>
    ): Response<Any>
}