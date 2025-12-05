package com.example.movilepopshoes.data.model.Carrito

import androidx.room.Embedded
import androidx.room.Relation
import com.example.movilepopshoes.data.model.Calzado

data class CarritoItemConCalzado(

    @Embedded
    val carritoItem: CarritoItem,


    @Relation(
        parentColumn = "calzadoId",
        entityColumn = "id"
    )
    val calzado: Calzado
)