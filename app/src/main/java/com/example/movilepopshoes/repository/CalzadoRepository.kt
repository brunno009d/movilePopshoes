package com.example.movilepopshoes.repository

import com.example.movilepopshoes.R
import com.example.movilepopshoes.data.remote.dao.CalzadoDao
import com.example.movilepopshoes.data.model.Calzado
import kotlinx.coroutines.flow.Flow


class CalzadoRepository(private val calzadoDao: CalzadoDao) {
    val allCalzados: Flow<List<Calzado>> = calzadoDao.getAllCalzados()
    fun getCalzadoById(id: Int): Flow<Calzado?> = calzadoDao.getCalzadoById(id)

    suspend fun popularDatosSiVacio() {
        if (calzadoDao.getCount() == 0) {
            getZapatosDePrueba().forEach { calzado ->
                calzadoDao.insert(calzado)
            }
        }
    }
}

// --- DATOS DE PRUEBA ---
private fun getZapatosDePrueba(): List<Calzado> {
    return listOf(
        Calzado(
            id = 1,
            nombre = "Adidas Samba",
            precio = 69990,
            talla = 42,
            imagenResId = R.drawable.adidassamba,
            descripcion = "Una zapatilla cómoda y con estilo para el día a día. Hecha con materiales reciclados."
        ),
        Calzado(
            id = 2,
            nombre = "Nike P-6000",
            precio = 89990,
            talla = 41,
            imagenResId = R.drawable.nikep6000,
            descripcion = "Perfecta para correr. Con tecnología de amortiguación avanzada y un diseño ligero."
        ),
        Calzado(
            id = 3,
            nombre = "Air Force",
            precio = 129990,
            talla = 43,
            imagenResId = R.drawable.airforce,
            descripcion = "Elegancia y durabilidad. Bota de cuero genuino, ideal para ocasiones formales o semi-formales."
        )
    )
}