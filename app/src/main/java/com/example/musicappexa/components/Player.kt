package com.example.musicappexa.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.musicappexa.models.Music
import com.example.musicappexa.services.ServiceRetrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Paleta
private val JetBlack = Color(0xFF0B0B0D)
private val DarkRed  = Color(0xFF7A0E0E)
private val Crimson  = Color(0xFFDC2626)
private val TextDim  = Color(0xFFEEECEE)

@Composable
fun Player(
    navController: NavController,
    contentPadding: PaddingValues
) {
    val isPreview = LocalInspectionMode.current

    var current by remember { mutableStateOf<Music?>(null) }
    var loading by remember { mutableStateOf(true) }
    var isPlaying by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            current = if (isPreview) {
                Music("Tales of Ithiria","Haggard","","https://m.media-amazon.com/images/I/51TLuWZMYeL._SY342_.jpg","1")
            } else {
                withContext(Dispatchers.IO) { ServiceRetrofit.musicService.getAllAlbums().firstOrNull() }
            }
        } catch (e: Exception) {
            Log.e("Player", "Error: ${e.message}")
        } finally { loading = false }
    }

    // Card con GRADIENTE ROJO y borde sutil para separarse del fondo negro
    Surface(
        color = Color.Transparent,
        shadowElevation = 12.dp,
        tonalElevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .clip(RoundedCornerShape(24.dp))
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.horizontalGradient(
                        listOf(Crimson, DarkRed)
                    )
                )
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(76.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Cover
                AsyncImage(
                    model = current?.image,
                    contentDescription = current?.title ?: "",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0x33000000)),
                    contentScale = ContentScale.Crop
                )

                Spacer(Modifier.width(12.dp))

                // Títulos
                Column(Modifier.weight(1f)) {
                    Text(
                        current?.title ?: "Unknown track",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1
                    )
                    Text(
                        current?.artist ?: "",
                        color = TextDim.copy(alpha = .9f),
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1
                    )
                }

                // Botón único (rojo ↔ blanco)
                val container = if (isPlaying) Color.White else JetBlack
                val iconTint  = if (isPlaying) Crimson else Color.White

                FilledIconButton(
                    onClick = { isPlaying = !isPlaying },
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = container)
                ) {
                    if (isPlaying) {
                        Icon(Icons.Filled.Pause, contentDescription = "Pause", tint = iconTint)
                    } else {
                        Icon(Icons.Filled.PlayArrow, contentDescription = "Play", tint = Color.White)
                    }
                }
            }
        }
    }
}
