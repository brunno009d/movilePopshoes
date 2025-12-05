package com.example.movilepopshoes

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.movilepopshoes.repository.UserRepository
import com.example.movilepopshoes.ui.screen.RegistroScreen
import com.example.movilepopshoes.viewmodel.MainViewModel
import com.example.movilepopshoes.viewmodel.UsuarioViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegistroScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testFormularioRegistroVisible() {
        // dependencias
        val userRepository = UserRepository()
        val usuarioViewModel = UsuarioViewModel(userRepository)
        val mainViewModel = MainViewModel()

        composeTestRule.setContent {
            RegistroScreen(
                viewModel = usuarioViewModel,
                mainViewModel = mainViewModel
            )
        }

        composeTestRule.onNodeWithText("Registro").assertIsDisplayed()

        composeTestRule.onNodeWithText("Nombre").assertIsDisplayed()
        composeTestRule.onNodeWithText("Direccion").assertIsDisplayed()

        composeTestRule.onNodeWithText("Nombre").performTextInput("Juanito Perez")
        composeTestRule.onNodeWithText("Juanito Perez").assertIsDisplayed()

        composeTestRule.onNodeWithText("Acepto los terminos y condiciones").assertIsDisplayed()
        composeTestRule.onNodeWithText("Registrar").assertIsDisplayed()

        composeTestRule.onNodeWithText("Acepto los terminos y condiciones").performClick()
    }
}