package com.example.movilepopshoes.data.remote.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.movilepopshoes.data.remote.model.Calzado

@Dao
interface CalzadoDao {
    @Insert
    suspend fun insertarCalzado(calzado: Calzado)

    // llamar todos calzados
    @Query("SELECT * FROM CALZADO")
    suspend fun obtenerCalzados(): List<Calzado>

    // llamar calzados por imagen y titulo

    // llamar todos los calzados segun categorias

    // llamar todos los calzados segun influencers


}