package com.example.movilepopshoes.data.remote.model.formularios

data class UsuarioUiState (
    val nombre: String = "",
    val correo: String = "",
    val clave: String = "",
    val direccion: String = "",
    val aceptaTerminos: Boolean = false,
    val errores: UsuarioErrores = UsuarioErrores(),
    )
{

}