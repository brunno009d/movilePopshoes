package com.example.movilepopshoes.data.remote

import com.example.movilepopshoes.data.remote.model.CalzadoApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PopShoesApiService {

    @GET("calzados")
    suspend fun obtenerTodosLosCalzados(): Response<List<CalzadoApi>>

    @GET("calzados/{id}")
    suspend fun obtenerCalzadoPorId(@Path("id") id: Int): Response<CalzadoApi>

    @GET("calzados/buscar/nombre")
    suspend fun buscarCalzado(@Query("nombre") nombre: String): Response<List<CalzadoApi>>

    @GET("https://mindicador.cl/api/dolar")
    suspend fun obtenerValorDolar(): Response<Any>
}