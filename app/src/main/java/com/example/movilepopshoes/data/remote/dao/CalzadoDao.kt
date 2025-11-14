package com.example.movilepopshoes.data.remote.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movilepopshoes.data.remote.model.Calzado
import kotlinx.coroutines.flow.Flow

@Dao
interface CalzadoDao {

    @Query("SELECT * FROM calzado")
    fun getAllCalzados(): Flow<List<Calzado>>

    @Query("SELECT * FROM calzado WHERE id = :id")
    fun getCalzadoById(id: Int): Flow<Calzado?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(calzado: Calzado)

    @Query("SELECT COUNT(*) FROM calzado")
    suspend fun getCount(): Int
}