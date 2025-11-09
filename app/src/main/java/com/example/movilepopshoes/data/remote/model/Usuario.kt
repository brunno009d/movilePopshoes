package com.example.movilepopshoes.data.remote.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuario")
data class Usuario (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val correo: String,
    val clave: String,
    val direccion: String,
    val aceptaTerminos: Boolean
){
}