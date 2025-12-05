package com.example.movilepopshoes.data.model

import com.google.gson.annotations.SerializedName

data class CompraRequest(
    val fecha: Long,
    val total: Int,
    val estado: String = "Pendiente",
    val direccion: String = "Dirección por defecto",

    val detalles: List<DetalleCompraRequest>,
    val usuario: ObjetoId,
    @SerializedName("metodoPago") val metodoPago: ObjetoId,
    @SerializedName("metodoEnvio") val metodoEnvio: ObjetoId
)

// Una Clase auxiliar para enviar solo el id de objetos relacionados
data class ObjetoId(
    val id: Int
)

// El detalle de cada zapato comprado
data class DetalleCompraRequest(
    val cantidad: Int,
    val precioUnitario: Int,
    val subtotal: Int,
    val calzado: ObjetoId
)