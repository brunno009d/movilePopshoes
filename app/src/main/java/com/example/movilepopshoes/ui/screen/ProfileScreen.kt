package com.example.movilepopshoes.ui.screen

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
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
import androidx.compose.ui.graphics.asImageBitmap
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

    val nombre by viewModel.nombre.collectAsState()
    val correo by viewModel.correo.collectAsState()
    val direccion by viewModel.direccion.collectAsState()
    val mensaje by viewModel.mensaje.collectAsState()
    val editando by viewModel.editando.collectAsState()

    var mostrarDialogoEliminar by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var bitmapPreview by remember { mutableStateOf<Bitmap?>(null) }

    val context = LocalContext.current

    LaunchedEffect(mensaje) {
        mensaje?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.limpiarMensaje()
        }
    }


    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bmp -> if (bmp != null) { bitmapPreview = bmp; imageUri = null } }

    val permissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted -> if (isGranted) takePictureLauncher.launch(null) }

    val selectImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri -> if (uri != null) { imageUri = uri; bitmapPreview = null } }

    if (mostrarDialogoEliminar) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoEliminar = false },
            title = { Text("¿Eliminar cuenta?") },
            text = { Text("Esta acción es irreversible.") },
            confirmButton = {
                TextButton(onClick = { mostrarDialogoEliminar = false; viewModel.eliminarCuenta(); mainViewModel.navigateTo(Screen.Inicio) },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)) { Text("Sí, eliminar") }
            },
            dismissButton = { TextButton(onClick = { mostrarDialogoEliminar = false }) { Text("Cancelar") } }
        )
    }

    Scaffold(topBar = { TopAppBar(title = { Text("Mi Perfil") }) }) { innerPadding ->
        Column(
            Modifier.fillMaxSize().padding(innerPadding).padding(16.dp).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (!logueado) {
                Spacer(modifier = Modifier.height(32.dp))
                Text("Inicia sesión para ver tu perfil")
                Button(onClick = { mainViewModel.navigateTo(Screen.Inicio) }, modifier = Modifier.fillMaxWidth()) { Text("Iniciar sesión") }
            } else {

                Box(
                    modifier = Modifier.size(150.dp).clip(CircleShape).border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    when {
                        bitmapPreview != null -> Image(bitmap = bitmapPreview!!.asImageBitmap(), contentDescription = "Nueva", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                        imageUri != null -> AsyncImage(model = imageUri, contentDescription = "Nueva", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                        usuario?.imagen != null -> AsyncImage(model = ImageRequest.Builder(LocalContext.current).data(usuario!!.imagen).crossfade(true).build(), contentDescription = "Actual", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                        else -> Icon(Icons.Default.Person, "Sin foto", Modifier.size(80.dp), tint = MaterialTheme.colorScheme.primary)
                    }
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    OutlinedButton(onClick = {
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) takePictureLauncher.launch(null)
                        else permissionsLauncher.launch(Manifest.permission.CAMERA)
                    }) { Text("📷 Cámara") }
                    OutlinedButton(onClick = { selectImageLauncher.launch("image/*") }) { Text("🖼️ Galería") }
                }

                if (bitmapPreview != null || imageUri != null) {
                    Button(
                        onClick = {
                            if (bitmapPreview != null) viewModel.subirFoto(bitmapPreview!!)
                            else if (imageUri != null) {
                                try {
                                    val bmp = if (Build.VERSION.SDK_INT < 28) MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
                                    else ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, imageUri!!))
                                    viewModel.subirFoto(bmp)
                                } catch (e: Exception) { }
                            }
                            bitmapPreview = null; imageUri = null
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                    ) { Text("💾 Guardar Nueva Foto") }
                }

                Divider()

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Información Personal", style = MaterialTheme.typography.titleMedium)
                    if (!editando) IconButton(onClick = { viewModel.activarEdicion() }) { Icon(Icons.Default.Edit, contentDescription = "Editar") }
                }

                val disabledColors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = Color.DarkGray, disabledBorderColor = Color.LightGray,
                    disabledLabelColor = Color.Gray, disabledContainerColor = Color(0xFFF5F5F5)
                )

                OutlinedTextField(value = nombre, onValueChange = { viewModel.onNombreChange(it) }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth(), singleLine = true, enabled = editando, colors = disabledColors)
                OutlinedTextField(value = correo, onValueChange = { viewModel.onCorreoChange(it) }, label = { Text("Correo") }, modifier = Modifier.fillMaxWidth(), singleLine = true, enabled = editando, colors = disabledColors)
                OutlinedTextField(value = direccion, onValueChange = { viewModel.onDireccionChange(it) }, label = { Text("Dirección") }, modifier = Modifier.fillMaxWidth(), enabled = editando, colors = disabledColors)

                if (editando) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = { viewModel.cancelarEdicion() }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)) { Text("Cancelar") }
                        Button(onClick = { viewModel.guardarDatos() }, modifier = Modifier.weight(1f)) { Text("Guardar") }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(onClick = { viewModel.logout(); mainViewModel.navigateTo(Screen.Inicio) }, modifier = Modifier.fillMaxWidth()) { Text("Cerrar sesión") }

                OutlinedButton(onClick = { mostrarDialogoEliminar = true }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error), border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.error)) { Text("Eliminar Cuenta") }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}