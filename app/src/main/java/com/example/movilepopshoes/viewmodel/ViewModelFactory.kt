package com.example.movilepopshoes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movilepopshoes.data.EstadoDataStore
import com.example.movilepopshoes.repository.UserRepository

class ViewModelFactory(
    private val repository: UserRepository,
    private val dataStore: EstadoDataStore
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(UsuarioViewModel::class.java) -> {
                UsuarioViewModel(repository, dataStore) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository, dataStore) as T
            }
            modelClass.isAssignableFrom(PerfilViewModel::class.java) -> {
                PerfilViewModel(repository, dataStore) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}
