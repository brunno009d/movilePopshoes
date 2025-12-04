package com.example.movilepopshoes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onStart

class CatalogoViewModel(private val repository: CalzadoRepository) : ViewModel() {

    val calzados: StateFlow<List<Calzado>?> = repository.allCalzados
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    private val _selectedProductId = MutableStateFlow<Int?>(null)

    val calzadoSeleccionado: StateFlow<Calzado?> = _selectedProductId.flatMapLatest { id ->
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

    fun selectCalzado(id: Int) {
        _selectedProductId.value = id
    }
}