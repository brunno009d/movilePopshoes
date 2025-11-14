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
import com.example.movilepopshoes.navigation.Screen
import com.example.movilepopshoes.viewmodel.LoginViewModel
import com.example.movilepopshoes.viewmodel.MainViewModel
import com.example.movilepopshoes.viewmodel.PerfilViewModel
import com.example.movilepopshoes.viewmodel.UsuarioViewModel

@Composable
fun ProfileScreen(
    mainViewModel: MainViewModel,
    viewModel: PerfilViewModel
) {
    val logueado by viewModel.logueado.collectAsState()
    val usuario by viewModel.usuario.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Perfil", style = MaterialTheme.typography.headlineMedium)

        if (!logueado) {
            Button(
                onClick = { mainViewModel.navigateTo(Screen.Inicio) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar sesión")
            }

            Button(
                onClick = { mainViewModel.navigateTo(Screen.Registro) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrarse")
            }
        } else {
            usuario?.let {
                Text("Nombre: ${it.nombre}")
                Text("Correo: ${it.correo}")
                Text("Dirección: ${it.direccion}")
            }

            Button(
                onClick = { viewModel.logout() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar sesión")
            }
        }
    }
}