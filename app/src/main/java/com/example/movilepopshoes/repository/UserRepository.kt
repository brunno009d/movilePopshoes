package com.example.movilepopshoes.repository

import com.example.movilepopshoes.data.remote.dao.UserDao
import com.example.movilepopshoes.data.remote.model.Usuario


class UserRepository(private  val userDao: UserDao){

    suspend fun registrarUsuario(usuario: Usuario) {
        userDao.añadirUsuario(usuario)
    }

    suspend fun login(correo: String, clave: String): Boolean {
        val usuario = userDao.obtenerUsuarioPorCorreo(correo)
        return usuario?.clave == clave
    }
}