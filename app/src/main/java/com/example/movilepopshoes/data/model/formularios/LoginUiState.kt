package com.example.movilepopshoes.data.model.formularios

data class LoginUiState(
    val correo: String = "",
    val clave: String = "",
    val logueado: Boolean = false,
    val errores: LoginErrores = LoginErrores()
) {
}