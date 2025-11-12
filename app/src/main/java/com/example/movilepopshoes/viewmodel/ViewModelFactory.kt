package com.example.movilepopshoes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movilepopshoes.data.EstadoDataStore
import com.example.movilepopshoes.data.remote.dao.UserDao
import com.example.movilepopshoes.data.remote.repository.UserRepository

class ViewModelFactory(
    private val repository: UserRepository,
    private val dataStore: EstadoDataStore
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsuarioViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UsuarioViewModel(repository, dataStore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
