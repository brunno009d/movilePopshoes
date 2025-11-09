package com.example.movilepopshoes.data.remote.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movilepopshoes.data.remote.model.Usuario

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun añadirUsuario(usuario: Usuario)

    @Query("SELECT * FROM usuario WHERE correo = :correo LIMIT 1")
    suspend fun obtenerUsuarioPorCorreo(correo: String): Usuario?
}