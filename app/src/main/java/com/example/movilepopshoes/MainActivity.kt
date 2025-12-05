package com.example.movilepopshoes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.movilepopshoes.navigation.AppNavigationGraph
import com.example.movilepopshoes.navigation.BottomBar
import com.example.movilepopshoes.navigation.BottomNavItem
import com.example.movilepopshoes.navigation.NavigationEvent
import com.example.movilepopshoes.viewmodel.CatalogoViewModel
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.movilepopshoes.data.EstadoDataStore
import com.example.movilepopshoes.repository.CalzadoRepository
import com.example.movilepopshoes.repository.CarritoRepository
import com.example.movilepopshoes.repository.CompraRepository
import com.example.movilepopshoes.repository.UserRepository
import com.example.movilepopshoes.viewmodel.CarritoViewModel
import com.example.movilepopshoes.viewmodel.LoginViewModel
import com.example.movilepopshoes.viewmodel.MainViewModel
import com.example.movilepopshoes.viewmodel.PerfilViewModel
import com.example.movilepopshoes.viewmodel.UsuarioViewModel
import com.example.movilepopshoes.viewmodel.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataStore = EstadoDataStore(applicationContext)

        val userRepository = UserRepository()
        val calzadoRepository = CalzadoRepository()
        val carritoRepository = CarritoRepository()
        val compraRepository = CompraRepository()

        val factory = ViewModelFactory(
            userRepository = userRepository,
            calzadoRepository = calzadoRepository,
            carritoRepository = carritoRepository,
            compraRepository = compraRepository,
            dataStore = dataStore
        )

        val usuarioViewModel: UsuarioViewModel by viewModels { factory }
        val loginViewModel: LoginViewModel by viewModels { factory }
        val perfilViewModel: PerfilViewModel by viewModels { factory }

        setContent {
            val mainViewModel: MainViewModel = viewModel()
            val catalogoViewModel: CatalogoViewModel = viewModel(factory = factory)
            val carritoViewModel: CarritoViewModel = viewModel(factory = factory)
            val navController = rememberNavController()

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
                AppNavigationGraph(
                    navController = navController,
                    mainViewModel = mainViewModel,
                    usuarioViewModel = usuarioViewModel,
                    catalogoViewModel = catalogoViewModel,
                    loginViewModel = loginViewModel,
                    perfilViewModel = perfilViewModel,
                    carritoViewModel = carritoViewModel,
                    innerPadding = innerPadding
                )
            }
        }
    }
}