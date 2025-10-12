package com.example.musicappexa.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.musicappexa.components.Albums
import com.example.musicappexa.components.Header
import com.example.musicappexa.components.Player
import com.example.musicappexa.components.RecentlyPlayed

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

        item{
            Player(
                navController = navController,
                contentPadding = contentPadding
            )
        }
    }

}