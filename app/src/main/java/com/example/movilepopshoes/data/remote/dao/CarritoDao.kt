package com.example.movilepopshoes.data.remote.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.movilepopshoes.data.remote.model.Carrito.CarritoItem
import com.example.movilepopshoes.data.remote.model.Carrito.CarritoItemConCalzado
import kotlinx.coroutines.flow.Flow

@Dao
interface CarritoDao {

    @Transaction
    @Query("SELECT * FROM carrito_items")
    fun getItemsConCalzado(): Flow<List<CarritoItemConCalzado>>

    @Query("SELECT * FROM carrito_items WHERE calzadoId = :calzadoId")
    suspend fun getItemPorCalzadoId(calzadoId: Int): CarritoItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CarritoItem)

    @Update
    suspend fun update(item: CarritoItem)

    @Delete
    suspend fun delete(item: CarritoItem)

    @Query("DELETE FROM carrito_items")
    suspend fun clearCart()

}