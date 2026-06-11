package com.example.filmvedizikasifi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint

import com.example.filmvedizikasifi.ui.screen.HomeView
import com.example.filmvedizikasifi.ui.screen.DetailView
import com.example.filmvedizikasifi.ui.screen.ProfileView
import com.example.filmvedizikasifi.ui.theme.FilmVeDiziKaşifiTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemTheme = isSystemInDarkTheme()
            var isDarkMode by remember { mutableStateOf(systemTheme) }

            FilmVeDiziKaşifiTheme(darkTheme = isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        enterTransition = {
                            slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(400)) + fadeIn(animationSpec = tween(400))
                        },
                        exitTransition = {
                            slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(400)) + fadeOut(animationSpec = tween(400))
                        },
                        popEnterTransition = {
                            slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(400)) + fadeIn(animationSpec = tween(400))
                        },
                        popExitTransition = {
                            slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(400)) + fadeOut(animationSpec = tween(400))
                        }
                    ) {
                        // 1. Durak: Ana Sayfa
                        composable("home") {
                            HomeView(
                                onMovieClick = { movieId ->
                                    navController.navigate("detail/$movieId")
                                },
                                onSearchClick = {
                                    navController.navigate("search")
                                },
                                onProfileClick = {
                                    navController.navigate("profile") // Profil sayfasına yönlendirme
                                }
                            )
                        }

                        // 2. Durak: Detay Sayfası
                        composable(
                            route = "detail/{movieId}",
                            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val movieId = backStackEntry.arguments?.getInt("movieId") ?: return@composable
                            DetailView(movieId = movieId)
                        }

                        // 3. Durak: Arama Sayfası
                        composable("search") {
                            com.example.filmvedizikasifi.ui.screen.SearchView(
                                onMovieClick = { movieId ->
                                    navController.navigate("detail/$movieId")
                                }
                            )
                        }

                        // 4. Durak: Profil Sayfası (Yeni Rota)
                        composable("profile") {
                            ProfileView(
                                isDarkMode = isDarkMode,
                                onThemeToggle = { isDarkMode = !isDarkMode }
                            )
                        }
                    }
                }
            }
        }
    }
}