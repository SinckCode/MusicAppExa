package com.example.musicappexa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.musicappexa.screens.HomeScreen
import com.example.musicappexa.screens.MusicDetailScreen
import com.example.musicappexa.ui.theme.HomeScreenRoute
import com.example.musicappexa.ui.theme.MusicAppExaTheme
import com.example.musicappexa.ui.theme.MusicDetailScreenRoute

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MusicAppExaTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = HomeScreenRoute
                    ){
                        composable<HomeScreenRoute>{
                            HomeScreen(
                                navController = navController,
                                contentPadding = innerPadding
                            )
                        }

                        composable<MusicDetailScreenRoute>{ backStack ->
                            val args = backStack.toRoute<MusicDetailScreenRoute>()
                            MusicDetailScreen(
                                //id = args.id,
                                contentPadding = innerPadding,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MusicAppExaTheme {

    }
}