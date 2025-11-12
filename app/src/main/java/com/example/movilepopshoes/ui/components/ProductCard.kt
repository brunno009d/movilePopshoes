package com.example.movilepopshoes.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.movilepopshoes.data.remote.model.Calzado

/**
 * Componente reutilizable que muestra la tarjeta de un producto (Calzado).
 */
@Composable
fun ProductCard(
    calzado: Calzado, // <-- Recibe tu objeto Calzado
    onProductClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable { onProductClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = calzado.imagenResId), // <-- Imagen de Calzado
                contentDescription = calzado.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f), // Imagen cuadrada
                contentScale = ContentScale.Crop
            )
            Box(modifier = Modifier.padding(12.dp)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = calzado.nombre, // <-- Nombre de Calzado
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        // Formateamos el Int como String de precio
                        text = "$${calzado.precio}", // <-- Precio de Calzado
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}