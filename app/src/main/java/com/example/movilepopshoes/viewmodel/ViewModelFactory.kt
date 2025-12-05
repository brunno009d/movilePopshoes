package com.example.movilepopshoes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movilepopshoes.data.EstadoDataStore
import com.example.movilepopshoes.repository.CalzadoRepository
import com.example.movilepopshoes.repository.CarritoRepository
import com.example.movilepopshoes.repository.CompraRepository
import com.example.movilepopshoes.repository.UserRepository

class ViewModelFactory(
    private val calzadoRepository: CalzadoRepository,
    private val userRepository: UserRepository,
    private val carritoRepository: CarritoRepository,
    private val compraRepository: CompraRepository,
    private val dataStore: EstadoDataStore
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(UsuarioViewModel::class.java) -> {
                UsuarioViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userRepository, dataStore) as T
            }
            modelClass.isAssignableFrom(PerfilViewModel::class.java) -> {
                PerfilViewModel(userRepository, dataStore) as T
            }
            modelClass.isAssignableFrom(CatalogoViewModel::class.java) -> {
                CatalogoViewModel(calzadoRepository) as T
            }
            modelClass.isAssignableFrom(CarritoViewModel::class.java) -> {
                CarritoViewModel(carritoRepository, compraRepository, dataStore) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}
