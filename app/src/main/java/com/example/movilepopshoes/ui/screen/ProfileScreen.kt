package com.example.movilepopshoes.ui.screen

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movilepopshoes.navigation.Screen
import com.example.movilepopshoes.viewmodel.MainViewModel
import com.example.movilepopshoes.viewmodel.PerfilViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    mainViewModel: MainViewModel,
    viewModel: PerfilViewModel
) {
    val logueado by viewModel.logueado.collectAsState()
    val usuario by viewModel.usuario.collectAsState()

    // Estados de datos
    val nombre by viewModel.nombre.collectAsState()
    val correo by viewModel.correo.collectAsState()
    val direccion by viewModel.direccion.collectAsState()
    val mensaje by viewModel.mensaje.collectAsState()

    // Estado de modo edición
    val editando by viewModel.editando.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(mensaje) {
        mensaje?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.limpiarMensaje()
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Mi Perfil") }) }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (!logueado) {
                // ... (Código para usuario no logueado se mantiene igual)
                Spacer(modifier = Modifier.height(32.dp))
                Text("Perfil", style = MaterialTheme.typography.headlineMedium)
                Button(onClick = { mainViewModel.navigateTo(Screen.Inicio) }) { Text("Iniciar sesión") }
            } else {

                // --- FOTO DE PERFIL (Solo visualización) ---
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    if (usuario?.imagen != null) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(usuario!!.imagen)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Foto de perfil",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(Icons.Default.Person, "Sin foto", Modifier.size(100.dp), tint = MaterialTheme.colorScheme.primary)
                    }
                }

                Divider()

                // --- FORMULARIO DE DATOS ---
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Información Personal", style = MaterialTheme.typography.titleMedium)

                    // Botón pequeño de editar si NO estamos editando
                    if (!editando) {
                        IconButton(onClick = { viewModel.activarEdicion() }) {
                            Icon(Icons.Default.Edit, contentDescription = "Editar")
                        }
                    }
                }

                // Configuración de colores para el modo "Deshabilitado/Gris"
                val disabledColors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = Color.DarkGray,
                    disabledBorderColor = Color.LightGray,
                    disabledLabelColor = Color.Gray,
                    disabledContainerColor = Color(0xFFF5F5F5) // Un gris muy clarito de fondo
                )

                OutlinedTextField(
                    value = nombre,
                    onValueChange = { viewModel.onNombreChange(it) },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    enabled = editando, // Se activa solo si editando es true
                    colors = disabledColors
                )

                OutlinedTextField(
                    value = correo,
                    onValueChange = { viewModel.onCorreoChange(it) },
                    label = { Text("Correo") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    enabled = editando,
                    colors = disabledColors
                )

                OutlinedTextField(
                    value = direccion,
                    onValueChange = { viewModel.onDireccionChange(it) },
                    label = { Text("Dirección") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = editando,
                    colors = disabledColors
                )

                Spacer(modifier = Modifier.height(8.dp))

                // --- BOTONES DINÁMICOS ---
                if (!editando) {
                    // MODO VISUALIZACIÓN: Solo mostramos botón de editar grande
                    Button(
                        onClick = { viewModel.activarEdicion() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Editar Datos")
                    }
                } else {
                    // MODO EDICIÓN: Mostramos Guardar y Cancelar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { viewModel.cancelarEdicion() },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                        ) {
                            Text("Cancelar")
                        }

                        Button(
                            onClick = { viewModel.guardarDatos() },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Guardar")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        viewModel.logout()
                        mainViewModel.navigateTo(Screen.Inicio)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cerrar sesión")
                }
            }
        }
    }
}