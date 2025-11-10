package com.example.movilepopshoes.data.remote.model

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "calzado")
data class Calzado (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val talla: Int,
    val precio: Int,
    val descripcion: String, // <-- AÑADIDO
    @DrawableRes val imagenResId: Int // <-- AÑADIDO (para res/drawable)
){
}