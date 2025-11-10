package com.example.movilepopshoes.navigation

// Usamos una clase "open" (abierta) en lugar de "sealed" (sellada)
// Esto nos da la flexibilidad de extenderla sin las restricciones de "sealed".
// Sigue funcionando igual para la navegación.
open class Screen (val route: String){

    // Tus pantallas (están perfectas)
    data object Home : Screen("home_page")
    data object Profile : Screen("profile_page")
    data object Settings : Screen("settings_page")
    data object Registro : Screen("registro_page")
    data object Resumen : Screen("resumen_page")
    data object Inicio : Screen("inicio_Sesion_page")
    data object Carrito : Screen("carrito_page")

    // --- CORRECCIÓN ---
    data object Detail : Screen("detail_page/{itemId}") {

        fun createRoute(itemId: Int): Screen {
            // Ahora creamos una *nueva instancia* de la clase base "Screen",
            // lo cual es permitido porque la clase ya no es "sealed".
            // Este objeto tendrá la ruta final que necesitamos (ej: "detail_page/1")
            return Screen("detail_page/$itemId")
        }
    }
}