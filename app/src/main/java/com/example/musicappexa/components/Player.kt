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

@Composable
fun Player(
    navController: NavController,
    contentPadding: PaddingValues
) {
    val isPreview = LocalInspectionMode.current

    var current by remember { mutableStateOf<Music?>(null) }
    var loading by remember { mutableStateOf(true) }
    var isPlaying by remember { mutableStateOf(false) } // << estado del botón

    LaunchedEffect(Unit) {
        try {
            current = if (isPreview) {
                Music(
                    title = "Tales of Ithiria",
                    artist = "Haggard",
                    description = "",
                    image = "https://m.media-amazon.com/images/I/51TLuWZMYeL._SY342_.jpg",
                    id = "1"
                )
            } else {
                withContext(Dispatchers.IO) {
                    ServiceRetrofit.musicService.getAllAlbums().firstOrNull()
                }
            }
        } catch (e: Exception) {
            Log.e("Player", "Error: ${e.message}")
        } finally {
            loading = false
        }
    }


    Surface(
        tonalElevation = 2.dp,
        shadowElevation = 6.dp,
        color = Color(0xFF2E1646), // morado oscuro
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(24.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(76.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Cover
            AsyncImage(
                model = current?.image,
                contentDescription = current?.title ?: "",
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF6A4AFF)),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.width(12.dp))


            Column(Modifier.weight(1f)) {
                Text(
                    current?.title ?: "Tales of Ithiria",
                    color = Color.White
                )
                Text(
                    current?.artist ?: "Haggard",
                    color = Color.White.copy(alpha = .7f),
                    style = MaterialTheme.typography.bodySmall
                )
            }


            FilledIconButton(
                onClick = { isPlaying = !isPlaying },
                colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color.White)
            ) {
                if (isPlaying) {
                    Icon(Icons.Filled.Pause, contentDescription = "Pause", tint = Color(0xFF2E1646))
                } else {
                    Icon(Icons.Filled.PlayArrow, contentDescription = "Play", tint = Color(0xFF2E1646))
                }
            }
        }
    }
}
