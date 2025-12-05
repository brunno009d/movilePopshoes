package com.example.movilepopshoes.data.remote.model

import com.google.gson.annotations.SerializedName

data class Usuario(
    val id: Int = 0,

    // --- CAMPOS OBLIGATORIOS (Login y Registro) ---
    val correo: String = "",

    @SerializedName("contrasena") // Mapea 'clave' de Android a 'contrasena' del Backend
    val clave: String = "",

    // --- CAMPOS OBLIGATORIOS EN REGISTRO (Con valores por defecto para que el Login no falle) ---
    val run: String = "",
    val nombre: String = "",

    // Coinciden exactamente con los nombres de variables en tu Backend (Usuario.java)
    val apaterno: String = "",
    val amaterno: String = "",
    val fechaNacimiento: String = "", // Se envía como String (ej: "2000-01-01")
    @SerializedName("fechaCreacion")
    val fechaCreacion: String = "",

    // --- CAMPOS OPCIONALES ---
    val direccion: String? = null,

    // Tu backend lo llama 'imagenUsuario', así que usamos ese nombre o SerializedName
    val imagenUsuario: String? = null,

    // Objeto anidado para el Rol (Obligatorio en registro, null en login local)
    val rol: Rol? = null
)

// Clase auxiliar para el Rol
data class Rol(
    val id: Int = 0,
    val nombre: String = ""
)