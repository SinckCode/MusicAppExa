package com.example.musicappexa.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.musicappexa.components.AboutDetail
import com.example.musicappexa.components.HeaderDetail
import com.example.musicappexa.components.TracksDetail
import com.example.musicappexa.ui.theme.MusicAppExaTheme

// Mismo color base que Home
private val JetBlack = Color(0xFF0B0B0D)

@Composable
fun MusicDetailScreen(
    id: String,
    navController: NavController,
    contentPadding: PaddingValues
) {
    Box(modifier = Modifier.background(JetBlack)) {
        LazyColumn(
            contentPadding = contentPadding
        ) {
            item {
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
            item {
                TracksDetail(
                    id = id,
                    navController = navController,
                    contentPadding = contentPadding
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0xFF0B0B0D)
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
