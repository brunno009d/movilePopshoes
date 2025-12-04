package com.example.movilepopshoes.data.remote.model

import com.google.gson.annotations.SerializedName

data class CalzadoApi(
    val id: Int,
    val nombre: String,
    val precio: Long,
    val descripcion: String?,
    val stock: Int,
    val marca: MarcaApi,
    val genero: GeneroApi,

    @SerializedName("imagen")
    val imagenUrl: String?
)
data class MarcaApi(
    val id: Int,
    val nombre: String
)

data class GeneroApi(
    val id: Int,
    val nombre: String
)