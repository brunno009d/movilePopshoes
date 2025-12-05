package com.example.movilepopshoes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movilepopshoes.data.EstadoDataStore
import com.example.movilepopshoes.data.remote.model.Usuario
import com.example.movilepopshoes.data.remote.model.formularios.PerfilUiState
import com.example.movilepopshoes.data.remote.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PerfilViewModel(
    private val repository: UserRepository,
    private val dataStore: EstadoDataStore
) : ViewModel() {

    val logueado: StateFlow<Boolean> = dataStore.usuario_log
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    private val _usuario = MutableStateFlow<PerfilUiState?>(null)
    val usuario: StateFlow<PerfilUiState?> = _usuario

    // --- NUEVO ESTADO: CONTROL DE EDICIÓN ---
    private val _editando = MutableStateFlow(false)
    val editando = _editando.asStateFlow()

    // Campos editables
    private val _nombre = MutableStateFlow("")
    val nombre = _nombre.asStateFlow()

    private val _correo = MutableStateFlow("")
    val correo = _correo.asStateFlow()

    private val _direccion = MutableStateFlow("")
    val direccion = _direccion.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje = _mensaje.asStateFlow()

    private var currentUserId: Int? = null

    init {
        viewModelScope.launch {
            dataStore.usuario_id.collect { id ->
                currentUserId = id
                if (id != null) {
                    val u = repository.obtenerUsuarioPorId(id)
                    u?.let {
                        // Guardamos el estado "original" en _usuario
                        _usuario.value = PerfilUiState(
                            nombre = it.nombre,
                            correo = it.correo,
                            direccion = it.direccion ?: "",
                            imagen = it.imagenUsuario
                        )
                        // Llenamos los campos editables
                        resetearCampos(it.nombre, it.correo, it.direccion ?: "")
                    }
                }
            }
        }
    }

    // Funciones de control de UI
    fun onNombreChange(v: String) { _nombre.value = v }
    fun onCorreoChange(v: String) { _correo.value = v }
    fun onDireccionChange(v: String) { _direccion.value = v }
    fun limpiarMensaje() { _mensaje.value = null }

    // --- NUEVAS FUNCIONES PARA EL BOTÓN EDITAR ---

    fun activarEdicion() {
        _editando.value = true
    }

    fun cancelarEdicion() {
        _editando.value = false
        // Restauramos los valores originales por si el usuario escribió algo y canceló
        _usuario.value?.let {
            resetearCampos(it.nombre, it.correo, it.direccion)
        }
    }

    private fun resetearCampos(nombre: String, correo: String, direccion: String) {
        _nombre.value = nombre
        _correo.value = correo
        _direccion.value = direccion
    }

    fun guardarDatos() {
        viewModelScope.launch {
            val id = currentUserId
            if (id != null) {
                val usuarioUpdate = Usuario(
                    id = id,
                    nombre = _nombre.value,
                    correo = _correo.value,
                    direccion = _direccion.value
                    // imagenUsuario = null
                )

                val exito = repository.actualizarDatos(id, usuarioUpdate)
                if (exito) {
                    _mensaje.value = "Datos actualizados correctamente"
                    _editando.value = false // Volvemos a bloquear los campos al guardar

                    // Actualizamos la fuente de verdad local
                    _usuario.value = _usuario.value?.copy(
                        nombre = _nombre.value,
                        correo = _correo.value,
                        direccion = _direccion.value
                    )
                } else {
                    _mensaje.value = "Error al actualizar datos"
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch { dataStore.cerrarSession() }
    }
}