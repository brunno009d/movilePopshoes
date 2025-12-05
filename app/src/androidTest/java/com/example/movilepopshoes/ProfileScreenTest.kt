package com.example.movilepopshoes

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.movilepopshoes.data.EstadoDataStore
import com.example.movilepopshoes.data.remote.repository.UserRepository
import com.example.movilepopshoes.ui.screen.ProfileScreen
import com.example.movilepopshoes.viewmodel.MainViewModel
import com.example.movilepopshoes.viewmodel.PerfilViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testPerfilSinSesionMuestraLogin() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val dataStore = EstadoDataStore(context)
        val userRepository = UserRepository()

        val perfilViewModel = PerfilViewModel(userRepository, dataStore)
        val mainViewModel = MainViewModel()

        // Cargar pantalla
        composeTestRule.setContent {
            ProfileScreen(
                mainViewModel = mainViewModel,
                viewModel = perfilViewModel
            )
        }

        // Verificamos

        // Verificar que aparezca el titulo
        composeTestRule.onNodeWithText("Mi Perfil").assertIsDisplayed()

        // Verificar que nos pide iniciar sesion
        composeTestRule.onNodeWithText("Inicia sesion para ver tu perfil").assertIsDisplayed()

        // Verificar que existe el boton para ir al login
        composeTestRule.onNodeWithText("Iniciar sesión").assertIsDisplayed()

        // Verificar que existe el boton registrarse
        composeTestRule.onNodeWithText("Registrarse").assertIsDisplayed()
    }
}