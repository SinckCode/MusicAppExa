package com.example.musicappexa.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Shuffle
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.musicappexa.components.AboutDetail
import com.example.musicappexa.components.HeaderDetail
import com.example.musicappexa.components.TracksDetail
import com.example.musicappexa.models.Music
import com.example.musicappexa.services.ServiceRetrofit
import com.example.musicappexa.ui.theme.MusicAppExaTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun MusicDetailScreen(
    id: String,
    navController: NavController,
    contentPadding: PaddingValues
) {


    LazyColumn {

        item{
            HeaderDetail(
                id = id,
                navController = navController,
                contentPadding = contentPadding
            )
        }

        item {
            AboutDetail(
                id = id,
                navController = navController,
                contentPadding = contentPadding
            )
        }

        item{
            TracksDetail(
                id = id,
                navController = navController,
                contentPadding = contentPadding
            )
        }

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MusicDetailScreenPreview() {
    MusicAppExaTheme {
        MusicDetailScreen(
            id = "682243ecf60db4caa642a48a",
            navController = rememberNavController(),
            contentPadding = PaddingValues(0.dp)
        )
    }
}