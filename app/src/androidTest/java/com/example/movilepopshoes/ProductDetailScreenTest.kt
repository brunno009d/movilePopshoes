package com.example.movilepopshoes

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.movilepopshoes.data.EstadoDataStore
import com.example.movilepopshoes.repository.CalzadoRepository
import com.example.movilepopshoes.repository.CarritoRepository
import com.example.movilepopshoes.repository.CompraRepository
import com.example.movilepopshoes.ui.screen.ProductDetailScreen
import com.example.movilepopshoes.viewmodel.CarritoViewModel
import com.example.movilepopshoes.viewmodel.CatalogoViewModel
import com.example.movilepopshoes.viewmodel.MainViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testPantallaDetalleCargando() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val dataStore = EstadoDataStore(context)

        val calzadoRepo = CalzadoRepository()
        val carritoRepo = CarritoRepository()
        val compraRepo = CompraRepository()

        // ViewModels
        val mainViewModel = MainViewModel()
        val catalogoViewModel = CatalogoViewModel(calzadoRepo)
        val carritoViewModel = CarritoViewModel(carritoRepo, compraRepo, dataStore)

        // Cargar pantalla
        composeTestRule.setContent {
            ProductDetailScreen(
                mainViewModel = mainViewModel,
                catalogoViewModel = catalogoViewModel,
                carritoViewModel = carritoViewModel
            )
        }

        // Verificando estado de carga
        composeTestRule.onNodeWithText("Cargando producto...").assertIsDisplayed()
    }
}