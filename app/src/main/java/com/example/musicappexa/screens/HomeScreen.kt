package com.example.musicappexa.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.musicappexa.components.Albums
import com.example.musicappexa.components.Header
import com.example.musicappexa.components.RecentlyPlayed
import com.example.musicappexa.ui.theme.MusicAppExaTheme

// Paleta (si ya la tienes en Theme.kt, usa esa)
private val JetBlack = Color(0xFF0B0B0D)

@Composable
fun HomeScreen(
    navController: NavController,
    contentPadding: PaddingValues
) {
    Box(modifier = Modifier.background(JetBlack)) {
        LazyColumn {
            item {
                Spacer(Modifier.height(40.dp))
            }
            item {
                Header(
                    navController = navController,
                    contentPadding = contentPadding
                )
            }
            item {
                Albums(
                    navController = navController,
                    contentPadding = contentPadding
                )
            }
            item {
                RecentlyPlayed(
                    navController = navController,
                    contentPadding = contentPadding
                )
            }
            item {
                Spacer(Modifier.height(80.dp))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0xFF0B0B0D)
@Composable
fun MusicScreenPreview() {
    MusicAppExaTheme {
        HomeScreen(
            navController = rememberNavController(),
            contentPadding = PaddingValues(0.dp)
        )
    }
}
