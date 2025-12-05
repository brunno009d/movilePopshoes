package com.example.movilepopshoes

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.movilepopshoes.data.EstadoDataStore
import com.example.movilepopshoes.data.remote.repository.CarritoRepository
import com.example.movilepopshoes.data.remote.repository.CompraRepository
import com.example.movilepopshoes.ui.screen.CarritoScreen
import com.example.movilepopshoes.viewmodel.CarritoViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CarritoScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testCarritoVacioMuestraMensaje() {
        // dependencias
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val dataStore = EstadoDataStore(context)
        val carritoRepo = CarritoRepository()
        val compraRepo = CompraRepository()

        // Creamos el ViewModel se iniciara con lista vacia
        val carritoViewModel = CarritoViewModel(carritoRepo, compraRepo, dataStore)

        composeTestRule.setContent {
            CarritoScreen(viewModel = carritoViewModel)
        }

        composeTestRule.onNodeWithText("Mi Carrito").assertIsDisplayed()

        // Verificar mensaje de estado vacío
        composeTestRule.onNodeWithText("Tu carrito está vacío.").assertIsDisplayed()
    }
}