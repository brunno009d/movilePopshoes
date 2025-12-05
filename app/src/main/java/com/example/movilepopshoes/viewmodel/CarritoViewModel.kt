package com.example.movilepopshoes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movilepopshoes.data.EstadoDataStore
import com.example.movilepopshoes.data.remote.model.Calzado
import com.example.movilepopshoes.data.remote.model.CompraRequest
import com.example.movilepopshoes.data.remote.model.DetalleCompraRequest
import com.example.movilepopshoes.data.remote.model.ObjetoId
import com.example.movilepopshoes.data.remote.repository.CarritoRepository
import com.example.movilepopshoes.data.remote.repository.CompraRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CarritoViewModel(
    private val carritoRepository: CarritoRepository,
    private val compraRepository: CompraRepository,
    private val dataStore: EstadoDataStore
) : ViewModel()
{
    val itemsEnCarrito = carritoRepository.itemsEnCarrito
    val totalCarrito: StateFlow<Int> = itemsEnCarrito.map { items ->
        items.sumOf { it.calzado.precio * it.cantidad }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    private val _compraEstado = MutableSharedFlow<Boolean>()
    val compraEstado = _compraEstado.asSharedFlow()
    private val _eventoCompletado = MutableSharedFlow<Unit>()
    val eventoCompletado = _eventoCompletado.asSharedFlow()

    fun agregarAlCarrito(calzado: Calzado) {
        carritoRepository.agregarAlCarrito(calzado)
    }

    fun eliminarDelCarrito(calzadoId: Int) {
        carritoRepository.eliminarDelCarrito(calzadoId)
    }

    fun aumentarCantidad(calzadoId: Int, cantidadActual: Int) {
        carritoRepository.actualizarCantidad(calzadoId, cantidadActual + 1)
    }

    fun disminuirCantidad(calzadoId: Int, cantidadActual: Int) {
        carritoRepository.actualizarCantidad(calzadoId, cantidadActual - 1)
    }

    fun finalizarCompra() {
        viewModelScope.launch {
            val items = itemsEnCarrito.first()
            val total = totalCarrito.first()
            val usuarioId = dataStore.usuario_id.first() // Obtenemos ID del usuario logueado

            if (items.isEmpty()) return@launch

            // Validar si es que el usuario no esta logueado
            if (usuarioId == null || usuarioId == -1) {
                println("Error: Usuario no logueado")
                return@launch
            }

            // Transformar items del Carrito a Detalles de la api
            val detallesApi = items.map { cartItem ->
                DetalleCompraRequest(
                    cantidad = cartItem.cantidad,
                    precioUnitario = cartItem.calzado.precio,
                    subtotal = cartItem.calzado.precio * cartItem.cantidad,
                    calzado = ObjetoId(cartItem.calzado.id)
                )
            }

            val fechaActual = System.currentTimeMillis()

            //  Armando la compra
            val compraRequest = CompraRequest(
                fecha = fechaActual,
                total = total,
                estado = "Pendiente",
                direccion = "Despacho a Domicilio",
                usuario = ObjetoId(usuarioId),
                metodoPago = ObjetoId(1),
                metodoEnvio = ObjetoId(1),
                detalles = detallesApi
            )

            // Enviar a la api
            val exito = compraRepository.realizarCompra(compraRequest)

            if (exito) {
                carritoRepository.vaciarCarrito() // Limpiar el carrito si se completo la compra
                _compraEstado.emit(true)
            } else {
                _compraEstado.emit(false)
            }
        }
    }
}