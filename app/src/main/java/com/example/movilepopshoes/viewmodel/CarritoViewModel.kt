package com.example.movilepopshoes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.movilepopshoes.data.remote.AppDatabase
import com.example.movilepopshoes.data.model.Calzado
import com.example.movilepopshoes.data.model.CarritoItem
import com.example.movilepopshoes.data.model.CarritoItemConCalzado
import com.example.movilepopshoes.repository.CarritoRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CarritoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CarritoRepository
    val itemsEnCarrito: StateFlow<List<CarritoItemConCalzado>>
    val totalCarrito: StateFlow<Int>
    private val _eventoCompletado = MutableSharedFlow<Unit>()
    val eventoCompletado = _eventoCompletado.asSharedFlow()

    init {
        val carritoDao = AppDatabase.getDatabase(application).carritoDao()
        repository = CarritoRepository(carritoDao)

        itemsEnCarrito = repository.itemsEnCarrito.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        totalCarrito = itemsEnCarrito.map { items ->
            items.sumOf { it.calzado.precio * it.carritoItem.cantidad }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )
    }


    fun agregarAlCarrito(calzado: Calzado) {
        viewModelScope.launch {
            repository.agregarAlCarrito(calzado)
        }
    }

    fun eliminarDelCarrito(item: CarritoItem) {
        viewModelScope.launch {
            repository.eliminarDelCarrito(item)
        }
    }

    fun aumentarCantidad(item: CarritoItem) {
        viewModelScope.launch {
            repository.actualizarCantidad(item, item.cantidad + 1)
        }
    }

    fun disminuirCantidad(item: CarritoItem) {
        viewModelScope.launch {
            repository.actualizarCantidad(item, item.cantidad - 1)
        }
    }

    fun finalizarCompra() {
        viewModelScope.launch {
            repository.vaciarCarrito()
            _eventoCompletado.emit(Unit)
        }
    }
}