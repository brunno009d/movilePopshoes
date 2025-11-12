package com.example.movilepopshoes.navigation


open class Screen (val route: String){


    data object Home : Screen("home_page")
    data object Profile : Screen("profile_page")
    data object Settings : Screen("settings_page")
    data object Registro : Screen("registro_page")
    data object Resumen : Screen("resumen_page")
    data object Inicio : Screen("inicio_Sesion_page")
    data object Carrito : Screen("carrito_page")


    data object Detail : Screen("detail_page/{itemId}") {

        fun createRoute(itemId: Int): Screen {
            return Screen("detail_page/$itemId")
        }
    }
}