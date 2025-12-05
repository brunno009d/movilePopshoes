package com.example.movilepopshoes

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.movilepopshoes.repository.CalzadoRepository
import com.example.movilepopshoes.ui.screen.HomeScreen
import com.example.movilepopshoes.viewmodel.CatalogoViewModel
import com.example.movilepopshoes.viewmodel.MainViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testHomeScreenTituloVisible() {
        // Instanciamos dependencias
        val calzadoRepository = CalzadoRepository()
        val catalogoViewModel = CatalogoViewModel(calzadoRepository)
        val mainViewModel = MainViewModel()

        composeTestRule.setContent {
            HomeScreen(
                mainViewModel = mainViewModel,
                catalogoViewModel = catalogoViewModel
            )
        }

        // Verificamos el titulo de la app
        composeTestRule.onNodeWithText("PopShoes").assertIsDisplayed()

        // Verificamos el mensaje de bienvenida
        composeTestRule.onNodeWithText("¡Bienvenido a PopShoes!").assertIsDisplayed()
    }
}