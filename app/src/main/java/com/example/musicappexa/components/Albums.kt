package com.example.musicappexa.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.musicappexa.models.Music
import com.example.musicappexa.services.ServiceRetrofit
import com.example.musicappexa.ui.theme.MusicAppExaTheme
import com.example.musicappexa.ui.theme.MusicDetailScreenRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun Albums(
    navController: NavController,
    contentPadding: PaddingValues
) {
    val isPreview = LocalInspectionMode.current
    var albums by remember { mutableStateOf<List<Music>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            albums = if (isPreview) {
                // Datos para Preview (sin red)
                listOf(
                    Music(
                        title = "Tales of Ithiria",
                        artist = "Haggard",
                        description = "",
                        image = "https://m.media-amazon.com/images/I/51TLuWZMYeL._SY342_.jpg",
                        id = "1"
                    ),
                    Music(
                        title = "Awake",
                        artist = "Avenged Sevenfold",
                        description = "",
                        image = "https://m.media-amazon.com/images/I/41o5xwkxupL._SX342_SY445_ControlCacheEqualizer_.jpg",
                        id = "2"
                    )
                )
            } else {
                // Llamada directa a Retrofit
                withContext(Dispatchers.IO) {
                    ServiceRetrofit.musicService.getAllAlbums()
                }
            }
        } catch (e: Exception) {
            error = e.message
            Log.e("Albums", "Error: ${e.message}")
        } finally {
            loading = false
        }
    }

    // Header
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Albums", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text(
            "See more",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF6D50FF),
            modifier = Modifier.clickable { /* TODO: navegar a listado */ }
        )
    }

    when {
        loading -> Row(Modifier.padding(16.dp)) { CircularProgressIndicator() }
        error != null -> Text("Error: $error", color = Color.Red, modifier = Modifier.padding(16.dp))
        else -> LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(end = 8.dp)
        ) {
            items(albums) { a ->
                AlbumCard(
                    title = a.title,
                    artist = a.artist,
                    imageUrl = a.image,
                    onClick = { navController.navigate(MusicDetailScreenRoute(a.id)) }
                )
            }
        }
    }
}

@Composable
private fun AlbumCard(
    title: String,
    artist: String,
    imageUrl: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(220.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(Color.White)
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = title,
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0x992E1646), Color(0xCC2E1646))
                    )
                )
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.widthIn(max = 150.dp)) {
                Text(
                    title,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    artist,
                    color = Color.White.copy(alpha = .9f),
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(Modifier.width(8.dp))
            Surface(
                modifier = Modifier.size(34.dp),
                shape = CircleShape,
                color = Color.White
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Filled.PlayArrow, contentDescription = "Play", tint = Color(0xFF2E1646))
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF3ECFF)
@Composable
fun AlbumsPreview() {
    MusicAppExaTheme {
        Albums(
            navController = rememberNavController(),
            contentPadding = PaddingValues(0.dp)
        )
    }
}