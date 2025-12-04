package com.example.movilepopshoes.data.model

import androidx.room.Embedded
import androidx.room.Relation


data class CarritoItemConCalzado(

    @Embedded
    val carritoItem: CarritoItem,


    @Relation(
        parentColumn = "calzadoId",
        entityColumn = "id"
    )
    val calzado: Calzado
)