package com.example.movilepopshoes.data.model

import com.google.gson.annotations.SerializedName

data class Calzado(
    val id: Int,
    val nombre: String,
    val precio: Int,
    val descripcion: String,
    val stock: Int,
    @SerializedName("imagen")
    val imagen: String? = null,
    val marca: Marca? = null,
    val genero: Genero? = null
)
data class Marca(
    val id: Int,
    val nombre: String
)

data class Genero(
    val id: Int,
    val nombre: String
)