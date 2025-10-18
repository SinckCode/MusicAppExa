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

// Paleta dark rojo/negro (puedes mover a Theme.kt)
private val JetBlack  = Color(0xFF0B0B0D)
private val NearBlack = Color(0xFF151518)
private val Graphite  = Color(0xFF1E1E22)
private val BloodRed  = Color(0xFFB71C1C)
private val Crimson   = Color(0xFFDC2626)

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
                        image = "https://m.media-amazon.com/images/I/41o5xwkxupL._SX342_SY445_.jpg",
                        id = "2"
                    )
                )
            } else {
                withContext(Dispatchers.IO) { ServiceRetrofit.musicService.getAllAlbums() }
            }
        } catch (e: Exception) {
            error = e.message
            Log.e("Albums", "Error: ${e.message}")
        } finally {
            loading = false
        }
    }

    // Encabezado
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "Albums",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )
        Text(
            "See more",
            style = MaterialTheme.typography.bodyMedium,
            color = Crimson,
            modifier = Modifier.clickable { /* TODO: navegar a listado */ }
        )
    }

    when {
        loading -> Row(Modifier.padding(16.dp)) { CircularProgressIndicator(color = Crimson) }
        error != null -> Text("Error: $error", color = Crimson, modifier = Modifier.padding(16.dp))
        else -> LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(end = 8.dp)
        ) {
            items(albums) { a ->
                AlbumCardDark(
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
private fun AlbumCardDark(
    title: String,
    artist: String,
    imageUrl: String,
    onClick: () -> Unit
) {
    // Tarjeta oscura con overlay rojo→negro y botón Play rojo
    Box(
        modifier = Modifier
            .width(220.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(Graphite) // borde y marco oscuro
            .clickable { onClick() }
    ) {
        // Portada
        AsyncImage(
            model = imageUrl,
            contentDescription = title,
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
                .clip(RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp)),
            contentScale = ContentScale.Crop
        )

        // Overlay para legibilidad del texto (de rojo sutil a negro)
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .height(88.dp)
                .clip(RoundedCornerShape(bottomStart = 22.dp, bottomEnd = 22.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            BloodRed.copy(alpha = 0.35f),
                            NearBlack.copy(alpha = 0.92f)
                        )
                    )
                )
        )

        // Contenido inferior (título, artista, play)
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
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
                    color = Color.White.copy(alpha = .85f),
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(Modifier.width(10.dp))
            FilledIconButton(
                onClick = onClick,
                shape = CircleShape,
                colors = IconButtonDefaults.filledIconButtonColors(containerColor = Crimson)
            ) {
                Icon(Icons.Filled.PlayArrow, contentDescription = "Play", tint = Color.White)
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0B0B0D, showSystemUi = true)
@Composable
fun AlbumsPreview() {
    MusicAppExaTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(JetBlack)
        ) {
            Albums(
                navController = rememberNavController(),
                contentPadding = PaddingValues(0.dp)
            )
        }
    }
}
