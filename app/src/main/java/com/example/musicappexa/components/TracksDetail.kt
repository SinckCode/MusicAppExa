package com.example.musicappexa.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
fun TracksDetail(
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
            Log.e("TracksDetail", "Error: ${e.message}", e)
        } finally {
            loading = false
        }
    }

    if (loading) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(120.dp),
            contentAlignment = Alignment.Center
        ) { CircularProgressIndicator() }
        return
    }
    if (error != null || album == null) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(120.dp),
            contentAlignment = Alignment.Center
        ) { Text("Error: ${error ?: "No data"}") }
        return
    }

    val a = album!!
    val tracks = remember(a.id) { (1..6).map { n -> "${a.title} • Track $n" } }

    Spacer(Modifier.height(12.dp))

    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(Modifier.height(4.dp))
        tracks.forEach { trackTitle ->
            TrackCard(
                imageUrl = a.image,
                title = trackTitle,
                subtitle = a.artist,
                onClick = { /* TODO: reproducir pista */ },
                onMore = { /* TODO: opciones */ }
            )
            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
private fun TrackCard(
    imageUrl: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    onMore: () -> Unit
) {
    Surface(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp)),
        shape = RoundedCornerShape(18.dp),
        color = Color.White,
        tonalElevation = 0.dp,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .clickable { onClick() }
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = title,
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFEFEFEF)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleMedium)
                Text(
                    subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF6C6C6C)
                )
            }
            IconButton(onClick = onMore) {
                Icon(Icons.Filled.MoreVert, contentDescription = "More")
            }
        }
    }
}

/* ================= Preview ================= */

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TracksDetailPreview() {
    MusicAppExaTheme {
        TracksDetail(
            id = "demo-123",
            navController = rememberNavController(),
            contentPadding = PaddingValues(0.dp)
        )
    }
}
