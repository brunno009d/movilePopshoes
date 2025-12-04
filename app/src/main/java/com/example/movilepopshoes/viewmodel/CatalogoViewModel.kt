package com.example.movilepopshoes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.movilepopshoes.data.remote.AppDatabase
import com.example.movilepopshoes.data.model.Calzado
import com.example.movilepopshoes.repository.CalzadoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Este ViewModel se encarga de toda la lógica de negocio
 * relacionada con el catálogo de productos (Calzados).
 */
class CatalogoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CalzadoRepository

    val calzados: StateFlow<List<Calzado>>

    private val _selectedProductId = MutableStateFlow<Int?>(null)
    val calzadoSeleccionado: StateFlow<Calzado?>

    init {
        // Inicia el Repositorio
        val calzadoDao = AppDatabase.getDatabase(application).calzadoDao()
        repository = CalzadoRepository(calzadoDao)

        // pone en la base de datos
        viewModelScope.launch(Dispatchers.IO) {
            repository.popularDatosSiVacio()
        }

        // conecta el flow de la lista de calzados
        calzados = repository.allCalzados.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        calzadoSeleccionado = _selectedProductId.flatMapLatest { id ->
            if (id == null) {
                flowOf(null)
            } else {
                repository.getCalzadoById(id)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
    }

    fun selectCalzado(id: Int) {
        _selectedProductId.value = id
    }
}