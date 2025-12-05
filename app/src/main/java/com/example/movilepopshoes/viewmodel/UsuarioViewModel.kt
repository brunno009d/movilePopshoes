package com.example.movilepopshoes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movilepopshoes.data.model.Rol
import com.example.movilepopshoes.data.model.Usuario
import com.example.movilepopshoes.data.model.formularios.UsuarioErrores
import com.example.movilepopshoes.data.model.formularios.UsuarioUiState
import com.example.movilepopshoes.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsuarioViewModel(
    private val repository: UserRepository
): ViewModel() {

    //estado interno mutable
    private val _estado = MutableStateFlow(UsuarioUiState())

    private val _registroExitoso = MutableStateFlow(false)
    val registroExitoso: StateFlow<Boolean> = _registroExitoso

    //Estado expuesto para la ui
    val estado: StateFlow<UsuarioUiState> = _estado

    // Actualizar el campo nombe y limpia su error
    fun onNombreChange(valor: String) {
        _estado.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null)) }
    }

    // Actualizar el campo correo
    fun onCorreoChange(valor: String){
        _estado.update { it.copy(correo = valor, errores = it.errores.copy(correo = null)) }
    }

    // Actualizar el campo clave
    fun onClaveChange(valor: String){
        _estado.update { it.copy(clave = valor, errores = it.errores.copy(clave = null)) }
    }

    // Actualizar el campo direccion
    fun onDireccionChange(valor: String){
        _estado.update { it.copy(direccion = valor, errores = it.errores.copy(direccion = null)) }
    }

    // Actualizar checkbox de aceptacion
    fun onAceptarTerminosChange(valor: Boolean){
        _estado.update { it.copy(aceptaTerminos = valor) }
    }

    fun validarFormulario(): Boolean {
        val estadoActual = _estado.value
        val errores = UsuarioErrores(
            nombre = if (estadoActual.nombre.isBlank()) "Campo Obligatorio" else null,
            correo = if (!estadoActual.correo.contains("@")) "Correo Invalido" else null,
            clave = if (estadoActual.clave.length < 6) "Debe tener al menos 6 caracteres" else null,
            direccion = if (estadoActual.direccion.isBlank()) "Campo Obligatorio" else null
        )

        val hayErrores = listOfNotNull(
            errores.nombre,
            errores.correo,
            errores.clave,
            errores.direccion
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }

        return !hayErrores
    }

    fun registrarUsuario() {
        viewModelScope.launch {
            val e = _estado.value
            val u = Usuario(
                nombre = e.nombre,
                correo = e.correo,
                clave = e.clave,
                direccion = e.direccion,
                run = "11.111.111-1",
                apaterno = "Apellido",
                amaterno = "Apellido",
                fechaNacimiento = "2000-01-01",
                fechaCreacion = "2023-11-25T10:00:00.000+00:00",
                rol = Rol(id = 2, nombre = "Cliente")
            )

            val exito = repository.registrarUsuario(u)

            if (exito) {
                _registroExitoso.value = true
            } else {
                println("Error al registrar usuario en API")
            }
        }
    }
    fun resetearExito() {
        _registroExitoso.value = false
    }
}