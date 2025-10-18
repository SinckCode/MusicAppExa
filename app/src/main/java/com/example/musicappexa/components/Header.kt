package com.example.musicappexa.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.musicappexa.screens.HomeScreen
import com.example.musicappexa.ui.theme.MusicAppExaTheme

// Tokens rápidos (puedes moverlos a tu Theme.kt)
private val JetBlack   = Color(0xFF0B0B0D)
private val NearBlack  = Color(0xFF141416)
private val BloodRed   = Color(0xFFB71C1C)
private val Crimson    = Color(0xFFDC2626)

@Composable
fun Header(
    navController: NavController,
    contentPadding: PaddingValues,
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(160.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(
                // Rojo en la parte superior que cae a negro profundo
                Brush.verticalGradient(
                    colors = listOf(
                        BloodRed,            // top
                        Crimson.copy(alpha = .75f),
                        NearBlack,
                        JetBlack             // bottom
                    )
                )
            )
            .padding(20.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    Icons.Rounded.Menu,
                    contentDescription = "Menu",
                    tint = Color.White
                )
                Icon(
                    Icons.Rounded.Search,
                    contentDescription = "Search",
                    tint = Color.White
                )
            }

            // Texto
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Welcome back,",
                    color = Color.White.copy(alpha = .9f),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Onesto Frias",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium
                )
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
