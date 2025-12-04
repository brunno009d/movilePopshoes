package com.example.movilepopshoes.repository

import com.example.movilepopshoes.data.remote.dao.CarritoDao
import com.example.movilepopshoes.data.model.Calzado
import com.example.movilepopshoes.data.model.CarritoItem
import com.example.movilepopshoes.data.model.CarritoItemConCalzado
import kotlinx.coroutines.flow.Flow

class CarritoRepository(private val carritoDao: CarritoDao) {

    val itemsEnCarrito: Flow<List<CarritoItemConCalzado>> = carritoDao.getItemsConCalzado()

    suspend fun agregarAlCarrito(calzado: Calzado) {
        val itemExistente = carritoDao.getItemPorCalzadoId(calzado.id)

        if (itemExistente != null) {
            val itemActualizado = itemExistente.copy(cantidad = itemExistente.cantidad + 1)
            carritoDao.update(itemActualizado)
        } else {
            val itemNuevo = CarritoItem(calzadoId = calzado.id, cantidad = 1)
            carritoDao.insert(itemNuevo)
        }
    }

    suspend fun eliminarDelCarrito(item: CarritoItem) {
        carritoDao.delete(item)
    }

    suspend fun actualizarCantidad(item: CarritoItem, nuevaCantidad: Int) {
        if (nuevaCantidad > 0) {
            val itemActualizado = item.copy(cantidad = nuevaCantidad)
            carritoDao.update(itemActualizado)
        } else {
            // Si la cantidad llega a 0 lo elimina
            carritoDao.delete(item)
        }
    }

    suspend fun vaciarCarrito() {
        carritoDao.clearCart()
    }
}