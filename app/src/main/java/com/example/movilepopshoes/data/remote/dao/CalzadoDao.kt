package com.example.movilepopshoes.data.remote.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movilepopshoes.data.model.Calzado
import kotlinx.coroutines.flow.Flow

@Dao
interface CalzadoDao {

    // Obtiene todos los calzados como un Flow (se actualiza solo)
    @Query("SELECT * FROM calzado")
    fun getAllCalzados(): Flow<List<Calzado>>

    // Obtiene un solo calzado por ID como un Flow
    @Query("SELECT * FROM calzado WHERE id = :id")
    fun getCalzadoById(id: Int): Flow<Calzado?>

    // Inserta un nuevo calzado
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(calzado: Calzado)

    // (Para popular la BD) Cuenta cuántos calzados hay
    @Query("SELECT COUNT(*) FROM calzado")
    suspend fun getCount(): Int
}