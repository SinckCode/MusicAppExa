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
                withContext(Dispatchers.IO) {
                    ServiceRetrofit.musicService.getAlbumById(id)
                }
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
        ) { CircularProgressIndicator() }
        return
    }
    if (error != null || album == null) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(360.dp),
            contentAlignment = Alignment.Center
        ) { Text("Error: ${error ?: "No data"}") }
        return
    }

    val a = album!!
    val DeepPurple = Color(0xFF2E1646)
    val AccentPurple = Color(0xFF6A4AFF)

    // --- estado del único botón (play/pause) ---
    var isPlaying by rememberSaveable(a.id) { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .fillMaxWidth()
            .height(360.dp)
            .clip(RoundedCornerShape(26.dp))
    ) {
        // portada
        AsyncImage(
            model = a.image,
            contentDescription = a.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // gradiente más marcado abajo para contraste
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        0f to Color.Transparent,
                        0.55f to DeepPurple.copy(alpha = 0.40f),
                        1f to DeepPurple.copy(alpha = 0.85f)
                    )
                )
        )

        // barra superior
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

        // textos + ÚNICO botón con estado
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

            val container =
                if (isPlaying) Color.White else AccentPurple
            val iconTint =
                if (isPlaying) DeepPurple else Color.White



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

@Preview(showBackground = true, showSystemUi = true)
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
