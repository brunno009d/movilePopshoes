package com.example.movilepopshoes

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.movilepopshoes.data.EstadoDataStore
import com.example.movilepopshoes.data.remote.repository.UserRepository
import com.example.movilepopshoes.ui.screen.LoginScreen
import com.example.movilepopshoes.viewmodel.LoginViewModel
import com.example.movilepopshoes.viewmodel.MainViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testComponentesLoginVisibles() {
        // dependencias
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val dataStore = EstadoDataStore(context)
        val userRepository = UserRepository()

        // Instanciar ViewModels
        val loginViewModel = LoginViewModel(userRepository, dataStore)
        val mainViewModel = MainViewModel()

        composeTestRule.setContent {
            LoginScreen(
                mainViewModel = mainViewModel,
                viewModel = loginViewModel
            )
        }

        // Verificamos

        composeTestRule.onNodeWithText("Inicio de Sesión").assertIsDisplayed()

        // Verificar texto del cuerpo
        // Usamos performScrollTo por si la pantalla es pequeña
        composeTestRule.onNodeWithText("Inicio de sesion")
            .performScrollTo()
            .assertIsDisplayed()

        // Verificar campos
        composeTestRule.onNodeWithText("Correo Electronico")
            .performScrollTo()
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Contraseña")
            .performScrollTo()
            .assertIsDisplayed()

        // 4. Verificar boton
        composeTestRule.onNodeWithText("Entrar")
            .performScrollTo()
            .assertIsDisplayed()

        // Prueba de escritura
        composeTestRule.onNodeWithText("Correo Electronico")
            .performTextInput("test@correo.com")

        composeTestRule.onNodeWithText("test@correo.com").assertIsDisplayed()
    }
}