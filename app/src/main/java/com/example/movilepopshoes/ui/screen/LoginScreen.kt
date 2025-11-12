package com.example.movilepopshoes.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movilepopshoes.viewmodel.UsuarioViewModel

@Composable
fun InicioSesionScreen(
    navController: NavController,
    viewModel: UsuarioViewModel
){
    val estado by viewModel.estado.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Inicio de sesion")

        OutlinedTextField(
            value = estado.correo,
            onValueChange = viewModel::onCorreoChange,
            label = { Text("Correo Electronico")},
            isError = estado.errores.correo != null,
            supportingText = {
                estado.errores.correo?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = estado.clave,
            onValueChange = viewModel::onClaveChange,
            label = { Text("Contraseña")},
            isError = estado.errores.clave != null,
            supportingText = {
                estado.errores.clave?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (viewModel.validarFormulario()) {
                    navController.navigate("resumen_page")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Entrar")
        }
    }
}