package com.example.movilepopshoes.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "carrito_items",
    indices = [Index(value = ["calzadoId"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = Calzado::class,
            parentColumns = ["id"],
            childColumns = ["calzadoId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CarritoItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val calzadoId: Int,
    val cantidad: Int
)