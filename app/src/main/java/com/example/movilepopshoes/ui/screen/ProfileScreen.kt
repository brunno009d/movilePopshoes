package com.example.movilepopshoes.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movilepopshoes.viewmodel.LoginViewModel
import com.example.movilepopshoes.viewmodel.UsuarioViewModel


// recibir el UsuarioViewModel
@Composable
fun ProfileScreen(
    viewModel: LoginViewModel,
    navController: NavController
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Mi Perfil", style = MaterialTheme.typography.headlineMedium)

        // Botón de logout
        Button(
            onClick = { viewModel.logOut() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar sesión")
        }

        // Navegar automáticamente al login cuando el usuario se desloguea
        val logueado by viewModel.logueado.collectAsState()
        LaunchedEffect(logueado) {
            if (!logueado) {
                navController.navigate("login_page") {
                    popUpTo("profile_page") { inclusive = true }
                }
            }
        }
    }
}