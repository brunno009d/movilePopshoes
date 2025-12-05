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
import com.example.movilepopshoes.viewmodel.CarritoViewModel
import com.example.movilepopshoes.viewmodel.CatalogoViewModel
import com.example.movilepopshoes.viewmodel.MainViewModel
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    mainViewModel: MainViewModel,
    catalogoViewModel: CatalogoViewModel,
    carritoViewModel: CarritoViewModel
) {
    val calzado by catalogoViewModel.calzadoSeleccionado.collectAsState()
    val itemsDelCarrito by carritoViewModel.itemsEnCarrito.collectAsState()

    val estaEnCarrito by remember(itemsDelCarrito, calzado) {
        derivedStateOf {
            itemsDelCarrito.any { it.calzado.id == calzado?.id }
        }
    }

    val colorBoton by animateColorAsState(
        targetValue = if (estaEnCarrito) Color.Gray else MaterialTheme.colorScheme.primary,
        animationSpec = tween(durationMillis = 500),
        label = "ColorBotonCarrito"
    )

    val textoBoton = if (estaEnCarrito) "En el Carrito" else "Agregar al Carrito"

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(calzado?.nombre ?: "Detalle del Producto")
                },
                navigationIcon = {
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
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(calzado!!.imagen)
                            .crossfade(true)
                            .build(),
                        contentDescription = calzado!!.nombre,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .align(Alignment.CenterHorizontally),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = calzado!!.nombre,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$${calzado!!.precio}",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = calzado!!.descripcion,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = {
                            if (!estaEnCarrito && calzado != null) {
                                carritoViewModel.agregarAlCarrito(calzado!!)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = colorBoton),
                        enabled = !estaEnCarrito,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        Text(textoBoton, fontSize = MaterialTheme.typography.bodyLarge.fontSize)
                    }
                }
            }
        }
    }
}