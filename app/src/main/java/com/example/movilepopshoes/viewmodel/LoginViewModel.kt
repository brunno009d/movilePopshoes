package com.example.movilepopshoes.viewmodel

import com.example.movilepopshoes.data.EstadoDataStore
import com.example.movilepopshoes.data.remote.model.UsuarioUiState
import com.example.movilepopshoes.data.remote.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel (
    private val repository: UserRepository,
    private val dataStore: EstadoDataStore
){

    private val _estado = MutableStateFlow(UsuarioUiState())
    val estado: StateFlow<UsuarioUiState> = _estado


}