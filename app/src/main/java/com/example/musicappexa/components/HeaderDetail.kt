package com.example.musicappexa.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.musicappexa.models.Music
import com.example.musicappexa.services.ServiceRetrofit
import com.example.musicappexa.ui.theme.MusicAppExaTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Paleta negro/rojo
private val JetBlack  = Color(0xFF0B0B0D)
private val NearBlack = Color(0xFF151518)
private val Crimson   = Color(0xFFDC2626)

@Composable
fun HeaderDetail(
    id: String,
    navController: NavController,
    contentPadding: PaddingValues
) {
    val isPreview = LocalInspectionMode.current

    var album by remember { mutableStateOf<Music?>(null) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(id) {
        try {
            album = if (isPreview) {
                Music(
                    id = id,
                    title = "Tales of Ithiria",
                    artist = "Haggard",
                    description = "Álbum sinfónico con elementos clásicos y death melódico.",
                    image = "https://i.imgur.com/XzY5R1x.jpg"
                )
            } else {
                withContext(Dispatchers.IO) { ServiceRetrofit.musicService.getAlbumById(id) }
            }
        } catch (e: Exception) {
            error = e.message ?: "Network error"
            Log.e("HeaderDetail", "Error: ${e.message}", e)
        } finally {
            loading = false
        }
    }

    if (loading) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(360.dp),
            contentAlignment = Alignment.Center
        ) { CircularProgressIndicator(color = Crimson) }
        return
    }
    if (error != null || album == null) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(360.dp),
            contentAlignment = Alignment.Center
        ) { Text("Error: ${error ?: "No data"}", color = Crimson) }
        return
    }

    val a = album!!
    var isPlaying by rememberSaveable(a.id) { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .fillMaxWidth()
            .height(360.dp)
            .clip(RoundedCornerShape(26.dp))
    ) {
        // Portada
        AsyncImage(
            model = a.image,
            contentDescription = a.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Overlay para contraste (ligero rojo → negro profundo)
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        0f to Color.Transparent,
                        0.45f to Crimson.copy(alpha = 0.35f),
                        1f to NearBlack.copy(alpha = 0.90f)
                    )
                )
        )

        // Barra superior
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            IconButton(onClick = { /* TODO: fav */ }) {
                Icon(Icons.Filled.FavoriteBorder, contentDescription = "Favorite", tint = Color.White)
            }
        }

        // Títulos + botón único
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text(
                text = a.title,
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = a.artist,
                color = Color.White.copy(alpha = .9f),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(12.dp))

            // Botón único (rojo↔blanco)
            val container = if (isPlaying) Crimson else Color.White
            val iconTint  = if (isPlaying) Color.White else Crimson

            FilledIconButton(
                onClick = { isPlaying = !isPlaying },
                colors = IconButtonDefaults.filledIconButtonColors(containerColor = container),
                shape = CircleShape,
                modifier = Modifier.size(48.dp)
            ) {
                if (isPlaying) {
                    Icon(Icons.Filled.Pause, contentDescription = "Pause", tint = iconTint)
                } else {
                    Icon(Icons.Filled.PlayArrow, contentDescription = "Play", tint = iconTint)
                }
            }
        }
    }
}

/* ===== Preview ===== */
@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0xFF0B0B0D)
@Composable
private fun HeaderDetailPreview() {
    MusicAppExaTheme {
        HeaderDetail(
            id = "682243ecf60db4caa642a48a",
            navController = rememberNavController(),
            contentPadding = PaddingValues(0.dp)
        )
    }
}
