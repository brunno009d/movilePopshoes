package com.example.movilepopshoes.data.remote.repository

import androidx.annotation.DrawableRes
import com.example.movilepopshoes.R
import com.example.movilepopshoes.data.remote.dao.CalzadoDao
import com.example.movilepopshoes.data.remote.model.Calzado
import kotlinx.coroutines.flow.Flow


class CalzadoRepository(private val calzadoDao: CalzadoDao) {
    val allCalzados: Flow<List<Calzado>> = calzadoDao.getAllCalzados()
    fun getCalzadoById(id: Int): Flow<Calzado?> = calzadoDao.getCalzadoById(id)

}

