package com.example.movilepopshoes.repository

import com.example.movilepopshoes.data.model.Calzado
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


data class CartItem(val calzado: Calzado, var cantidad: Int)

class CarritoRepository {
    private val _items = MutableStateFlow<List<CartItem>>(emptyList())
    val itemsEnCarrito: StateFlow<List<CartItem>> = _items.asStateFlow()

    fun agregarAlCarrito(calzado: Calzado) {
        _items.update { currentItems ->
            val existente = currentItems.find { it.calzado.id == calzado.id }
            if (existente != null) {
                currentItems.map {
                    if (it.calzado.id == calzado.id) it.copy(cantidad = it.cantidad + 1) else it
                }
            } else {
                currentItems + CartItem(calzado, 1)
            }
        }
    }

    fun eliminarDelCarrito(calzadoId: Int) {
        _items.update { currentItems ->
            currentItems.filter { it.calzado.id != calzadoId }
        }
    }

    fun actualizarCantidad(calzadoId: Int, nuevaCantidad: Int) {
        if (nuevaCantidad <= 0) {
            eliminarDelCarrito(calzadoId)
            return
        }
        _items.update { currentItems ->
            currentItems.map {
                if (it.calzado.id == calzadoId) it.copy(cantidad = nuevaCantidad) else it
            }
        }
    }

    fun vaciarCarrito() {
        _items.value = emptyList()
    }
}