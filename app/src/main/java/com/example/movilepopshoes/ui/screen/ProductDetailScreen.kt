package com.example.movilepopshoes.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.movilepopshoes.viewmodel.CatalogoViewModel // <-- IMPORTAR
import com.example.movilepopshoes.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    // --- CORRECCIÓN: Recibir ambos VMs ---
    mainViewModel: MainViewModel,
    catalogoViewModel: CatalogoViewModel
) {
    // --- Usar el catalogoViewModel ---
    val calzado by catalogoViewModel.calzadoSeleccionado.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(calzado?.nombre ?: "Detalle del Producto")
                },
                navigationIcon = {
                    // --- Usar el mainViewModel ---
                    IconButton(onClick = { mainViewModel.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver atrás"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (calzado == null) {
                CircularProgressIndicator()
                Text("Cargando producto...")
            } else {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    // 1. Imagen
                    Image(
                        painter = painterResource(id = calzado!!.imagenResId),
                        contentDescription = calzado!!.nombre,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .align(Alignment.CenterHorizontally),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // 2. Nombre
                    Text(
                        text = calzado!!.nombre,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // 3. Precio
                    Text(
                        text = "$${calzado!!.precio}",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // 4. Talla
                    Text(
                        text = "Talla: ${calzado!!.talla}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // 5. Descripción
                    Text(
                        text = calzado!!.descripcion,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // 6. Botón Agregar al Carrito
                    Button(
                        onClick = { /* TODO: Lógica para agregar al carrito */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        Text("Agregar al Carrito", fontSize = MaterialTheme.typography.bodyLarge.fontSize)
                    }
                }
            }
        }
    }
}