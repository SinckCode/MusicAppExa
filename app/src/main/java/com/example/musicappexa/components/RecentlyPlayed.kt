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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.musicappexa.models.Music
import com.example.musicappexa.services.ServiceRetrofit
import com.example.musicappexa.ui.theme.MusicDetailScreenRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Paleta rojo/negro (mueve a Theme.kt si prefieres)
private val JetBlack  = Color(0xFF0B0B0D)
private val Graphite  = Color(0xFF1E1E22)
private val TextDim   = Color(0xFFB3B3B6)
private val Crimson   = Color(0xFFDC2626)

@Composable
fun RecentlyPlayed(
    navController: NavController,
    contentPadding: PaddingValues
) {
    val isPreview = LocalInspectionMode.current

    var items by remember { mutableStateOf<List<Music>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            items = if (isPreview) {
                listOf(
                    Music("Tales of Ithiria","Haggard","","https://m.media-amazon.com/images/I/51TLuWZMYeL._SY342_.jpg","1"),
                    Music("Awake","Avenged Sevenfold","","https://m.media-amazon.com/images/I/41o5xwkxupL._SX342_SY445_ControlCacheEqualizer_.jpg","2"),
                    Music("Nightmare","Avenged Sevenfold","","https://m.media-amazon.com/images/I/81D5il1PpPL._AC_SL1425_.jpg","3"),
                    Music("Abbey Road","The Beatles","","https://upload.wikimedia.org/wikipedia/en/4/42/Beatles_-_Abbey_Road.jpg","4")
                )
            } else {
                withContext(Dispatchers.IO) { ServiceRetrofit.musicService.getAllAlbums() }
            }
        } catch (e: Exception) {
            error = e.message
            Log.e("RecentlyPlayed", "Error: ${e.message}")
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
        Text(
            "Recently Played",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )
        Text(
            "See more",
            style = MaterialTheme.typography.bodyMedium,
            color = Crimson
        )
    }

    when {
        loading -> Row(Modifier.padding(16.dp)) { CircularProgressIndicator(color = Crimson) }
        error != null -> Text("Error: $error", color = Crimson, modifier = Modifier.padding(16.dp))
        else -> Column {
            items.forEach { track ->
                RecentlyPlayedItemDark(
                    title = track.title,
                    artist = track.artist,
                    imageUrl = track.image,
                    onClickMore = { navController.navigate(MusicDetailScreenRoute(track.id)) }
                )
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun RecentlyPlayedItemDark(
    title: String,
    artist: String,
    imageUrl: String,
    onClickMore: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Graphite)
            .padding(12.dp)
            .clickable(onClick = onClickMore),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = title,
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(
                title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(2.dp))
            Text(
                "$artist • Popular Song",
                style = MaterialTheme.typography.bodySmall,
                color = TextDim,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        IconButton(onClick = onClickMore) {
            Icon(Icons.Filled.MoreVert, contentDescription = "More", tint = Color.White)
        }
    }
}
