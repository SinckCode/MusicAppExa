package com.example.musicappexa.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.musicappexa.screens.HomeScreen
import com.example.musicappexa.ui.theme.MusicAppExaTheme

@Composable
fun Header(
    navController: NavController,
    contentPadding: PaddingValues,
    userName: String = "Onesto Frias"
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(160.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(
                Brush.linearGradient(
                    listOf(Color(0xFF9D7BFF), Color(0xFF7C4DFF))
                )
            )
            .padding(20.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp)
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                    Icon(Icons.Rounded.Menu, contentDescription = "Menu", tint = Color.White)




                    Icon(Icons.Rounded.Search, contentDescription = "Search", tint = Color.White)

            }

            // Texto
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Good Morning!",
                    color = Color.White.copy(alpha = .95f),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = userName,
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold
                )
            }
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
