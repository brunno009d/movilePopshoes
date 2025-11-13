package com.example.movilepopshoes.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.movilepopshoes.ui.screen.CarritoScreen
import com.example.movilepopshoes.ui.screen.HomeScreen
import com.example.movilepopshoes.ui.screen.InicioSesionScreen
import com.example.movilepopshoes.ui.screen.ProductDetailScreen
import com.example.movilepopshoes.ui.screen.ProfileScreen
import com.example.movilepopshoes.ui.screen.RegistroScreen
import com.example.movilepopshoes.ui.screen.ResumenScreen
import com.example.movilepopshoes.ui.screen.SettingsScreen
import com.example.movilepopshoes.viewmodel.CarritoViewModel
import com.example.movilepopshoes.viewmodel.CatalogoViewModel
import com.example.movilepopshoes.viewmodel.MainViewModel
import com.example.movilepopshoes.viewmodel.UsuarioViewModel

@Composable
fun AppNavigationGraph(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    usuarioViewModel: UsuarioViewModel,
    catalogoViewModel: CatalogoViewModel,
    carritoViewModel: CarritoViewModel,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                navController = navController,
                mainViewModel = mainViewModel,
                catalogoViewModel = catalogoViewModel
            )
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(viewModel = usuarioViewModel)
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
            CarritoScreen(
                navController = navController,
                viewModel = carritoViewModel
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
        ) { backStackEntry ->

            val itemId = backStackEntry.arguments?.getInt("itemId")
            requireNotNull(itemId) { "El itemId no puede ser nulo" }

            catalogoViewModel.selectCalzado(itemId)

            ProductDetailScreen(
                mainViewModel = mainViewModel,
                catalogoViewModel = catalogoViewModel,
                carritoViewModel = carritoViewModel
            )
        }
    }
}