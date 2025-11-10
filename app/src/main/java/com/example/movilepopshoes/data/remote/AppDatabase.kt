package com.example.movilepopshoes.data.remote

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movilepopshoes.data.remote.dao.CalzadoDao
import com.example.movilepopshoes.data.remote.dao.UserDao
import com.example.movilepopshoes.data.remote.model.Calzado
import com.example.movilepopshoes.data.remote.model.Usuario

// --- CAMBIO 1 ---
// La versión se incrementa a 2 porque cambiamos la tabla "calzado"
@Database(entities = [Usuario::class, Calzado::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun calzadoDao(): CalzadoDao

    companion object{
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "popshoes_db"
                )
                    // --- CAMBIO 2 ---
                    // Le dice a Room que destruya y recree la BD si la versión cambia.
                    // Esto es perfecto para desarrollo.
                    .fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }
        }
    }
}