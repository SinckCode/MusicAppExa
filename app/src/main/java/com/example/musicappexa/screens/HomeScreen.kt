package com.example.musicappexa.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.musicappexa.components.Albums
import com.example.musicappexa.components.Header
import com.example.musicappexa.components.Player
import com.example.musicappexa.components.RecentlyPlayed
import com.example.musicappexa.ui.theme.MusicAppExaTheme

@Composable
fun HomeScreen(
    navController: NavController,
    contentPadding: PaddingValues
) {


    LazyColumn{
        item{
            Header(
                navController = navController,
                contentPadding = contentPadding
            )
        }

        item{
            Albums(
                navController = navController,
                contentPadding = contentPadding
            )
        }

        item{
            RecentlyPlayed(
                navController = navController,
                contentPadding = contentPadding
            )
        }

    }

}

@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0xFFF3ECFF)
@Composable
fun MusicScreenPreview() {
    MusicAppExaTheme {
        HomeScreen(
            navController = rememberNavController(),
            contentPadding = PaddingValues(0.dp)
        )
    }
}
