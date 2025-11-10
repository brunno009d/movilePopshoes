package com.example.movilepopshoes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.movilepopshoes.data.remote.AppDatabase
import com.example.movilepopshoes.data.remote.model.Calzado
import com.example.movilepopshoes.data.remote.repository.CalzadoRepository
import com.example.movilepopshoes.navigation.NavigationEvent
import com.example.movilepopshoes.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// 1. Cambiar a AndroidViewModel para obtener el Context de la App
class MainViewModel(application: Application) : AndroidViewModel(application) {

    // (Tu código de navegación que ya tenías)
    private val _navigationEvents = MutableSharedFlow<NavigationEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    fun navigateTo(screen: Screen) {
        viewModelScope.launch {
            _navigationEvents.emit(NavigationEvent.NavigateTo(screen))
        }
    }
    fun navigateUp() {
        viewModelScope.launch {
            _navigationEvents.emit(NavigationEvent.NavigateUp)
        }
    }

    // --- NUEVA LÓGICA DE DATOS ---

    private val repository: CalzadoRepository

    // 2. Exponer la lista de calzados (se actualiza automáticamente desde Room)
    val calzados: StateFlow<List<Calzado>>

    // 3. Exponer el calzado seleccionado
    private val _selectedProductId = MutableStateFlow<Int?>(null)
    val calzadoSeleccionado: StateFlow<Calzado?>

    init {
        // 4. Inicializar el Repositorio
        val calzadoDao = AppDatabase.getDatabase(application).calzadoDao()
        repository = CalzadoRepository(calzadoDao)

        // 5. Poblar la base de datos (en un hilo de IO)
        viewModelScope.launch(Dispatchers.IO) {
            repository.popularDatosSiVacio()
        }

        // 6. Conectar el Flow de la lista de calzados
        calzados = repository.allCalzados.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        // 7. Lógica reactiva para el calzado seleccionado
        // (Esto es avanzado pero es la forma correcta)
        // Transforma el "ID seleccionado" en el "Objeto Calzado" completo
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

    // 8. El NavHost llamará a esta función
    fun selectCalzado(id: Int) {
        _selectedProductId.value = id
    }
}