package com.example.movilepopshoes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movilepopshoes.data.EstadoDataStore
import com.example.movilepopshoes.data.model.formularios.LoginErrores
import com.example.movilepopshoes.data.model.formularios.LoginUiState
import com.example.movilepopshoes.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: UserRepository,
    private val dataStore: EstadoDataStore
) : ViewModel() {

    private val _estado = MutableStateFlow(LoginUiState())
    val estado: StateFlow<LoginUiState> = _estado

    val logueado: StateFlow<Boolean> = dataStore.usuario_log
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun onCorreoChange(valor: String) {
        _estado.update { it.copy(correo = valor, errores = it.errores.copy(correo = null)) }
    }

    fun onClaveChange(valor: String) {
        _estado.update { it.copy(clave = valor, errores = it.errores.copy(clave = null)) }
    }

    fun validarFormularioLogin(): Boolean {
        val estadoActual = _estado.value
        val errores = LoginErrores(
            correo = if (estadoActual.correo.isEmpty()) "El correo no puede estar vacio" else null,
            clave = if (estadoActual.clave.isEmpty()) "La contraseña no puede estar vacia" else null
        )

        val hayErrores = listOfNotNull(
            errores.correo,
            errores.clave
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }
        return !hayErrores
    }

    fun loguin() {
        if (!validarFormularioLogin()) return

        viewModelScope.launch {
            val correo = _estado.value.correo
            val clave = _estado.value.clave

            // LLAMADA A LA API
            val usuario = repository.login(correo, clave)

            if (usuario != null) {
                // Login exitoso: Guardamos sesión
                dataStore.guardarSession(usuario.id, true)
                _estado.update { it.copy(logueado = true) }
            } else {
                // Login fallido
                _estado.update {
                    it.copy(errores = it.errores.copy(correo = "Correo o Contraseña Incorrectos"))
                }
            }
        }
    }

    fun logOut(){
        viewModelScope.launch {
            dataStore.cerrarSession()
        }
    }
}