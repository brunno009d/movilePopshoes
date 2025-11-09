package com.example.movilepopshoes.data.remote

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movilepopshoes.data.remote.dao.CalzadoDao
import com.example.movilepopshoes.data.remote.dao.UserDao
import com.example.movilepopshoes.data.remote.model.Calzado
import com.example.movilepopshoes.data.remote.model.Usuario

@Database(entities = [Usuario::class, Calzado::class], version = 1)
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
                ).build().also { INSTANCE = it }
            }
        }
    }
}