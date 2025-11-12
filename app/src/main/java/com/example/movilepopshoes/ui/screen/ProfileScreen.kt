package com.example.movilepopshoes.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movilepopshoes.viewmodel.UsuarioViewModel


// recibir el UsuarioViewModel
@Composable
fun ProfileScreen(
    viewModel: UsuarioViewModel
) {
    // Nos conectamos al 'estado' del UsuarioViewModel
    val estado by viewModel.estado.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        Text("Mi Perfil", style = MaterialTheme.typography.headlineMedium)

        if (estado.nombre.isBlank()) {
            // Si no hay datos (ej. no se ha registrado o iniciado sesión)
            Text("No hay información de usuario disponible.")
        } else {
            // Si hay datos en el estado
            Text("Nombre: ${estado.nombre}")
            Text("Correo: ${estado.correo}")
            Text("Direccion: ${estado.direccion}")
            Text("Terminos: ${if (estado.aceptaTerminos) "Aceptados" else "No aceptados"}")
        }

    }
}