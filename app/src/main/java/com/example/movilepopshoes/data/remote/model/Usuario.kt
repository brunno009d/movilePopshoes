package com.example.movilepopshoes.data.remote.model

import com.google.gson.annotations.SerializedName

data class Usuario(
    val id: Int = 0,
    val nombre: String,
    val correo: String,

    // Usamos @SerializedName para conectar tu variable "clave" con el campo "contrasena" del JSON
    @SerializedName("contrasena")
    val clave: String,

    val direccion: String? = null,

    // Campos opcionales que tu backend tiene
    val run: String? = null,
    val apaterno: String? = null,
    val amaterno: String? = null,
    val fechaNacimiento: String? = null, // Las fechas en JSON suelen viajar como String

    // Rol viene como objeto anidado
    val rol: Rol? = null
)

data class Rol(
    val id: Int,
    val nombre: String
)