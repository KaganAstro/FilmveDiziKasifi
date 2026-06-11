package com.example.filmvedizikasifi.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.filmvedizikasifi.R
import com.example.filmvedizikasifi.data.Resource

@Composable
fun DetailView(
    movieId: Int,
    viewModel: DetailViewModel = hiltViewModel()
) {
    LaunchedEffect(movieId) {
        viewModel.getDetail(movieId)
    }

    val detailState by viewModel.movieDetail.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .verticalScroll(rememberScrollState())
    ) {
        when (val resource = detailState) {
            is Resource.Loading -> {
                Box(modifier = Modifier.fillMaxSize().height(400.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.Red)
                }
            }
            is Resource.Success -> {
                val movie = resource.data
                if (movie != null) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/original${movie.posterPath}",
                        contentDescription = movie.title,
                        modifier = Modifier.fillMaxWidth().height(500.dp),
                        contentScale = ContentScale.Crop
                    )
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = movie.title ?: stringResource(R.string.unknown_movie),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "⭐ ${stringResource(R.string.rating)}: ${movie.voteAverage} | 📅 ${stringResource(R.string.release)}: ${movie.releaseDate}",
                            fontSize = 14.sp,
                            color = Color.LightGray
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        val genresText = movie.genres?.joinToString(", ") { it.name } ?: ""
                        Text(
                            text = "🎭 ${stringResource(R.string.genres)}: $genresText",
                            fontSize = 14.sp,
                            color = Color.LightGray
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = stringResource(R.string.overview),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = movie.overview ?: "",
                            fontSize = 16.sp,
                            color = Color.White,
                            lineHeight = 24.sp
                        )
                    }
                }
            }
            is Resource.Error -> {
                Box(modifier = Modifier.fillMaxSize().height(400.dp), contentAlignment = Alignment.Center) {
                    Text(text = "${stringResource(R.string.error)}: ${resource.message}", color = Color.Red)
                }
            }
            null -> {}
        }
    }
}