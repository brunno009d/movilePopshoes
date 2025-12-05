package com.example.movilepopshoes.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movilepopshoes.data.EstadoDataStore
import com.example.movilepopshoes.data.model.Usuario
import com.example.movilepopshoes.data.model.formularios.PerfilUiState
import com.example.movilepopshoes.repository.UserRepository
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

    private val _editando = MutableStateFlow(false)
    val editando = _editando.asStateFlow()

    private val _nombre = MutableStateFlow("")
    val nombre = _nombre.asStateFlow()

    private val _correo = MutableStateFlow("")
    val correo = _correo.asStateFlow()

    private val _direccion = MutableStateFlow("")
    val direccion = _direccion.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje = _mensaje.asStateFlow()

    private var _fotoPendienteUri: Uri? = null

    private var currentUserId: Int? = null

    init {
        viewModelScope.launch {
            dataStore.usuario_id.collect { id ->
                currentUserId = id
                if (id != null) {
                    val u = repository.obtenerUsuarioPorId(id)
                    u?.let {
                        _usuario.value = PerfilUiState(
                            nombre = it.nombre,
                            correo = it.correo,
                            direccion = it.direccion ?: "",
                            imagen = it.imagenUsuario
                        )
                        resetearCampos(it.nombre, it.correo, it.direccion ?: "")
                    }
                }
            }
        }
    }

    fun onNombreChange(v: String) { _nombre.value = v }
    fun onCorreoChange(v: String) { _correo.value = v }
    fun onDireccionChange(v: String) { _direccion.value = v }
    fun limpiarMensaje() { _mensaje.value = null }

    fun activarEdicion() { _editando.value = true }

    fun cancelarEdicion() {
        _editando.value = false
        _fotoPendienteUri = null // Limpiamos la foto pendiente
        _usuario.value?.let { resetearCampos(it.nombre, it.correo, it.direccion) }
    }

    fun onFotoSeleccionada(uri: Uri) {
        _fotoPendienteUri = uri
    }

    private fun resetearCampos(nombre: String, correo: String, direccion: String) {
        _nombre.value = nombre
        _correo.value = correo
        _direccion.value = direccion
    }

    fun guardarTodo(context: Context) {
        viewModelScope.launch {
            val id = currentUserId
            if (id == null) return@launch

            _mensaje.value = "Procesando..."

            var urlFinalImagen = _usuario.value?.imagen

            if (_fotoPendienteUri != null) {
                _mensaje.value = "Subiendo imagen a la nube..."
                val urlSubida = repository.subirImagenImgBB(context, _fotoPendienteUri!!)

                if (urlSubida != null) {
                    urlFinalImagen = urlSubida
                } else {
                    _mensaje.value = "Error al subir la imagen. Intenta de nuevo."
                    return@launch
                }
            }

            _mensaje.value = "Guardando perfil..."

            val usuarioUpdate = Usuario(
                id = id,
                nombre = _nombre.value,
                correo = _correo.value,
                direccion = _direccion.value,
                imagenUsuario = urlFinalImagen
            )

            val exito = repository.actualizarDatos(id, usuarioUpdate)

            if (exito) {
                _mensaje.value = "¡Perfil actualizado correctamente!"
                _editando.value = false
                _fotoPendienteUri = null

                // Actualizamos la UI
                _usuario.value = _usuario.value?.copy(
                    nombre = _nombre.value,
                    correo = _correo.value,
                    direccion = _direccion.value,
                    imagen = urlFinalImagen
                )
            } else {
                _mensaje.value = "Error al guardar los datos."
            }
        }
    }

    fun eliminarCuenta() {
        viewModelScope.launch {
            val id = currentUserId
            if (id != null) {
                if (repository.eliminarCuenta(id)) dataStore.cerrarSession()
                else _mensaje.value = "Error al eliminar la cuenta"
            }
        }
    }

    fun logout() {
        viewModelScope.launch { dataStore.cerrarSession() }
    }
}