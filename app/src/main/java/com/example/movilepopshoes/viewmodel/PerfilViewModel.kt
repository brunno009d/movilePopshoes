package com.example.movilepopshoes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movilepopshoes.data.EstadoDataStore
import com.example.movilepopshoes.data.remote.model.formularios.PerfilUiState
import com.example.movilepopshoes.data.remote.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PerfilViewModel (
    private val repository: UserRepository,
    private val dataStore: EstadoDataStore
) : ViewModel(){

    val logueado: StateFlow<Boolean> = dataStore.usuario_log
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    //Estado dinamico del usuario
    private val _usuario = MutableStateFlow<PerfilUiState?>(null)
    val usuario: StateFlow<PerfilUiState?> = _usuario

    init {
        viewModelScope.launch {
            dataStore.usuario_id.collect { id ->
                if (id != null) {
                    val u = repository.obtenerUsuarioPorId(id)
                    _usuario.value = u?.let {
                        PerfilUiState(
                            nombre = it.nombre,
                            correo = it.correo,
                            direccion = it.direccion
                        )
                    }
                } else {
                    _usuario.value = null
                }
            }
        }
    }

    fun logout(){
        viewModelScope.launch {
            dataStore.cerrarSession()
        }
    }

}