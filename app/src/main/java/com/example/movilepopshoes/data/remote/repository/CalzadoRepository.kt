package com.example.movilepopshoes.data.remote.repository

import androidx.annotation.DrawableRes
import com.example.movilepopshoes.R
import com.example.movilepopshoes.data.remote.dao.CalzadoDao
import com.example.movilepopshoes.data.remote.model.Calzado
import kotlinx.coroutines.flow.Flow

// El repositorio necesita el DAO para funcionar
class CalzadoRepository(private val calzadoDao: CalzadoDao) {

    // Expone el Flow de datos directamente desde el DAO
    val allCalzados: Flow<List<Calzado>> = calzadoDao.getAllCalzados()

    // Expone la búsqueda por ID
    fun getCalzadoById(id: Int): Flow<Calzado?> = calzadoDao.getCalzadoById(id)

    // --- Lógica para poblar la base de datos con datos de prueba ---
    // (En una app real, esto vendría de una API)
    suspend fun popularDatosSiVacio() {
        if (calzadoDao.getCount() == 0) {
            // Si la BD está vacía, inserta estos datos
            getZapatosDePrueba().forEach { calzado ->
                calzadoDao.insert(calzado)
            }
        }
    }
}

// --- DATOS DE PRUEBA ---
// (Los movimos aquí, al repositorio)
private fun getZapatosDePrueba(): List<Calzado> {
    return listOf(
        Calzado(
            id = 1,
            nombre = "Adidas Samba",
            precio = 69990,
            talla = 42,
            imagenResId = R.drawable.adidassamba, // (Debes agregar 'zapato_urbano.png' a res/drawable)
            descripcion = "Una zapatilla cómoda y con estilo para el día a día. Hecha con materiales reciclados."
        ),
        Calzado(
            id = 2,
            nombre = "Nike P-6000",
            precio = 89990,
            talla = 41,
            imagenResId = R.drawable.nikep6000, // (Debes agregar 'zapato_running.png' a res/drawable)
            descripcion = "Perfecta para correr. Con tecnología de amortiguación avanzada y un diseño ligero."
        ),
        Calzado(
            id = 3,
            nombre = "Air Force",
            precio = 129990,
            talla = 43,
            imagenResId = R.drawable.airforce, // (Debes agregar 'zapato_bota.png' a res/drawable)
            descripcion = "Elegancia y durabilidad. Bota de cuero genuino, ideal para ocasiones formales o semi-formales."
        )
    )
}