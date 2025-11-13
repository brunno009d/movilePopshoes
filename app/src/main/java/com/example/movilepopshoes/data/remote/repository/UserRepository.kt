package com.example.movilepopshoes.data.remote.repository

import com.example.movilepopshoes.data.remote.dao.UserDao
import com.example.movilepopshoes.data.remote.model.Usuario

class UserRepository(private  val userDao: UserDao){

    suspend fun registrarUsuario(usuario: Usuario) {
        userDao.añadirUsuario(usuario)
    }

    suspend fun obtenerUsuarioPorCorreo(correo: String): Usuario? {
        return userDao.obtenerUsuarioPorCorreo(correo)
    }

    suspend fun obtenerUsuarioPorId(id: Int): Usuario? {
        return userDao.obtenerUsuarioPorId(id)
    }
}