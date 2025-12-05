package com.example.movilepopshoes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movilepopshoes.data.remote.model.Calzado
import com.example.movilepopshoes.data.remote.repository.CarritoRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CarritoViewModel(private val repository: CarritoRepository) : ViewModel() {
    val itemsEnCarrito = repository.itemsEnCarrito

    val totalCarrito: StateFlow<Int> = itemsEnCarrito.map { items ->
        items.sumOf { it.calzado.precio * it.cantidad }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    private val _eventoCompletado = MutableSharedFlow<Unit>()
    val eventoCompletado = _eventoCompletado.asSharedFlow()

    fun agregarAlCarrito(calzado: Calzado) {
        repository.agregarAlCarrito(calzado)
    }

    fun eliminarDelCarrito(calzadoId: Int) {
        repository.eliminarDelCarrito(calzadoId)
    }

    fun aumentarCantidad(calzadoId: Int, cantidadActual: Int) {
        repository.actualizarCantidad(calzadoId, cantidadActual + 1)
    }

    fun disminuirCantidad(calzadoId: Int, cantidadActual: Int) {
        repository.actualizarCantidad(calzadoId, cantidadActual - 1)
    }

    fun finalizarCompra() {
        viewModelScope.launch {
            repository.vaciarCarrito()
            _eventoCompletado.emit(Unit)
        }
    }
}