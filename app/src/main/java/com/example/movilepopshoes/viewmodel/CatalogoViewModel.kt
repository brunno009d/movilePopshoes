package com.example.movilepopshoes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.movilepopshoes.data.remote.AppDatabase
import com.example.movilepopshoes.data.remote.model.Calzado
import com.example.movilepopshoes.data.remote.repository.CalzadoRepository
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

    // 1. Exponer la lista de calzados (se actualiza automáticamente desde Room)
    val calzados: StateFlow<List<Calzado>>

    // 2. Exponer el calzado seleccionado
    private val _selectedProductId = MutableStateFlow<Int?>(null)
    val calzadoSeleccionado: StateFlow<Calzado?>

    init {
        // 3. Inicializar el Repositorio
        val calzadoDao = AppDatabase.getDatabase(application).calzadoDao()
        repository = CalzadoRepository(calzadoDao)

        // 4. Poblar la base de datos (en un hilo de IO)
        viewModelScope.launch(Dispatchers.IO) {
            repository.popularDatosSiVacio()
        }

        // 5. Conectar el Flow de la lista de calzados
        calzados = repository.allCalzados.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        // 6. Lógica reactiva para el calzado seleccionado
        calzadoSeleccionado = _selectedProductId.flatMapLatest { id ->
            if (id == null) {
                flowOf(null) // Si no hay ID, emite nulo
            } else {
                repository.getCalzadoById(id) // Si hay ID, busca en el repo
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
    }

    // 7. El NavHost llamará a esta función
    fun selectCalzado(id: Int) {
        _selectedProductId.value = id
    }
}