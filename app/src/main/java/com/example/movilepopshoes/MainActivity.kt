package com.example.movilepopshoes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType // <-- ASEGÚRATE DE IMPORTAR ESTO
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument // <-- ASEGÚRATE DE IMPORTAR ESTO
import com.example.movilepopshoes.data.EstadoDataStore
import com.example.movilepopshoes.data.remote.AppDatabase
import com.example.movilepopshoes.data.remote.repository.UserRepository
import com.example.movilepopshoes.navigation.BottomBar
import com.example.movilepopshoes.navigation.BottomNavItem
import com.example.movilepopshoes.navigation.NavigationEvent
import com.example.movilepopshoes.navigation.Screen
import com.example.movilepopshoes.ui.screen.CarritoScreen
import com.example.movilepopshoes.ui.screen.ProfileScreen
import com.example.movilepopshoes.ui.screen.HomeScreen
import com.example.movilepopshoes.ui.screen.InicioSesionScreen
// --- IMPORTAR LA NUEVA PANTALLA ---
import com.example.movilepopshoes.ui.screen.ProductDetailScreen
import com.example.movilepopshoes.ui.screen.SettingsScreen
import com.example.movilepopshoes.ui.screen.ResumenScreen
import com.example.movilepopshoes.ui.screen.RegistroScreen
import com.example.movilepopshoes.viewmodel.MainViewModel
import com.example.movilepopshoes.viewmodel.UsuarioViewModel
import com.example.movilepopshoes.viewmodel.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mainViewModel: MainViewModel = viewModel()
            val navController = rememberNavController()

            //Para la base de datos
            val db = AppDatabase.getDatabase(applicationContext)
            val userDao = db.userDao()
            val repository = UserRepository(userDao)
            val dataStore = EstadoDataStore(applicationContext)
            val usuarioViewModel: UsuarioViewModel = viewModel(factory = ViewModelFactory(repository, dataStore))


            LaunchedEffect(key1 = Unit) {
                mainViewModel.navigationEvents.collectLatest { event ->
                    when (event) {
                        is NavigationEvent.NavigateTo -> {
                            navController.navigate(event.route.route) {
                                event.popUpTORoute?.let {
                                    popUpTo(it.route) {
                                        inclusive = event.inclusive
                                    }

                                }
                                launchSingleTop = event.singleTop
                                restoreState = true
                            }


                        }
                        is NavigationEvent.PopBackStack -> navController.popBackStack()
                        is NavigationEvent.NavigateUp -> navController.navigateUp()
                    }
                }
            }

            Scaffold(
                bottomBar = {
                    BottomBar(
                        navController = navController,
                        items = listOf(
                            BottomNavItem.Carrito,
                            BottomNavItem.Home,
                            BottomNavItem.Perfil
                        )
                    )
                }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = Screen.Home.route,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(route = Screen.Home.route) {
                        HomeScreen(navController = navController, viewModel = mainViewModel)
                    }
                    composable(route = Screen.Profile.route) {
                        ProfileScreen()
                    }
                    composable(route = Screen.Settings.route) {
                        SettingsScreen(navController = navController, viewModel = mainViewModel)
                    }
                    composable(route = Screen.Registro.route) {
                        RegistroScreen(mainViewModel = mainViewModel, viewModel = usuarioViewModel)
                    }
                    composable(route = Screen.Resumen.route) {
                        ResumenScreen(viewModel = usuarioViewModel)
                    }
                    composable(route = Screen.Inicio.route) {
                        InicioSesionScreen(navController = navController, viewModel = usuarioViewModel)
                    }
                    composable(route = Screen.Carrito.route) {
                        CarritoScreen(navController = navController)
                    }

                    // --- PASO 7: AÑADIR ESTA RUTA ---
                    composable(
                        route = Screen.Detail.route, // "detail_page/{itemId}"
                        arguments = listOf(navArgument("itemId") { type = NavType.IntType }) // "itemId"
                    ) { backStackEntry ->

                        // 1. Obtiene el ID (ej: "1", "2") de la ruta
                        val itemId = backStackEntry.arguments?.getInt("itemId")
                        requireNotNull(itemId) { "El itemId no puede ser nulo" }

                        // 2. ¡LÍNEA CLAVE! Le dice al ViewModel qué producto cargar
                        mainViewModel.selectCalzado(itemId)

                        // 3. Muestra la pantalla de detalle que creaste en el Paso 6
                        ProductDetailScreen(viewModel = mainViewModel)
                    }
                    // --- FIN DEL PASO 7 ---
                }
            }
        }
    }
}