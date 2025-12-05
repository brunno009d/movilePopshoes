package com.example.movilepopshoes.data.model

import com.google.gson.annotations.SerializedName

data class Usuario(
    val id: Int = 0,
    val correo: String = "",

    @SerializedName("contrasena")
    val clave: String = "",
    val run: String = "",
    val nombre: String = "",
    val apaterno: String = "",
    val amaterno: String = "",
    val fechaNacimiento: String = "",
    val fechaCreacion: String = "",
    val direccion: String? = null,
    val imagenUsuario: String? = null,
    val rol: Rol? = null
)

// Clase auxiliar para el Rol
data class Rol(
    val id: Int = 0,
    val nombre: String = ""
)