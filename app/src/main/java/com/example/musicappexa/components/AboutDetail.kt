package com.example.musicappexa.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.musicappexa.models.Music
import com.example.musicappexa.services.ServiceRetrofit
import com.example.musicappexa.ui.theme.MusicAppExaTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Paleta negro/rojo
private val Graphite = Color(0xFF1E1E22)
private val TextDim  = Color(0xFFB3B3B6)
private val Crimson  = Color(0xFFDC2626)

@Composable
fun AboutDetail(
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
                    description = "Un álbum sinfónico que mezcla elementos de música clásica con death metal melódico.",
                    image = "https://m.media-amazon.com/images/I/51TLuWZMYeL._SY342_.jpg"
                )
            } else {
                withContext(Dispatchers.IO) { ServiceRetrofit.musicService.getAlbumById(id) }
            }
        } catch (e: Exception) {
            error = e.message ?: "Network error"
            Log.e("AboutDetail", "Error: ${e.message}", e)
        } finally {
            loading = false
        }
    }

    if (loading) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(120.dp),
            contentAlignment = Alignment.Center
        ) { CircularProgressIndicator(color = Crimson) }
        return
    }
    if (error != null || album == null) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(120.dp),
            contentAlignment = Alignment.Center
        ) { Text("Error: ${error ?: "No data"}", color = Crimson) }
        return
    }

    val a = album!!

    Column(modifier = Modifier.fillMaxWidth()) {
        // Card oscura con sombra suave
        Surface(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            color = Graphite,
            tonalElevation = 0.dp,
            shadowElevation = 6.dp
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    "About this album",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    a.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextDim
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        // Chip de artista (una sola píldora)
        Surface(
            modifier = Modifier.padding(horizontal = 16.dp),
            shape = RoundedCornerShape(50),
            color = Graphite,
            tonalElevation = 0.dp,
            shadowElevation = 2.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Artist:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Crimson,
                    maxLines = 1
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    a.artist,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    maxLines = 1
                )
            }
        }
    }
}

/* ============ Preview ============ */
@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0xFF0B0B0D)
@Composable
private fun AboutDetailPreview() {
    MusicAppExaTheme {
        AboutDetail(
            id = "demo-123",
            navController = rememberNavController(),
            contentPadding = PaddingValues(0.dp)
        )
    }
}
