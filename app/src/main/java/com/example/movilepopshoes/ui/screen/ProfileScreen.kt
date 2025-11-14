package com.example.movilepopshoes.ui.screen

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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


    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bmp ->
        if (bmp != null) {
            bitmap = bmp
            imageUri = null // Limpiar la URI de la galería
        }
    }

    val permissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            takePictureLauncher.launch(null)
        } else {
            Toast.makeText(context, "Permiso de cámara denegado 😞", Toast.LENGTH_SHORT).show()
        }
    }

    val selectImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            imageUri = uri
            bitmap = null // Limpiar el bitmap de la cámara
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Mi Perfil") })
        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally, // Centrar todo
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            if (!logueado) {
                Spacer(modifier = Modifier.height(32.dp))
                Text("Perfil", style = MaterialTheme.typography.headlineMedium)

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

                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    when {
                        bitmap != null -> {
                            Image(
                                bitmap = bitmap!!.asImageBitmap(),
                                contentDescription = "Foto de perfil tomada",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        imageUri != null -> {
                            Image(
                                painter = rememberAsyncImagePainter(imageUri),
                                contentDescription = "Foto de perfil seleccionada",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        else -> {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Placeholder de perfil",
                                modifier = Modifier.size(100.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = {
                        val permissionCheckResult = ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.CAMERA
                        )
                        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                            takePictureLauncher.launch(null)
                        } else {
                            permissionsLauncher.launch(Manifest.permission.CAMERA)
                        }
                    }) {
                        Text("📷 Tomar Foto")
                    }

                    Button(onClick = { selectImageLauncher.launch("image/*") }) {
                        Text("🖼️ Galería")
                    }
                }

                Divider()

                usuario?.let {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text("Nombre: ${it.nombre}", style = MaterialTheme.typography.bodyLarge)
                        Text("Correo: ${it.correo}", style = MaterialTheme.typography.bodyLarge)
                        Text("Dirección: ${it.direccion}", style = MaterialTheme.typography.bodyLarge)
                    }
                }

                Button(
                    onClick = {
                        viewModel.logout()
                        mainViewModel.navigateTo(Screen.Inicio)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cerrar sesión")
                }
            }
        }
    }
}