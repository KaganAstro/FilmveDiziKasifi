package com.example.filmvedizikasifi.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.filmvedizikasifi.R
import com.example.filmvedizikasifi.data.Resource
import com.example.filmvedizikasifi.data.model.MovieResponse

@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit,
    onSearchClick: () -> Unit,
    onProfileClick: () -> Unit // Profil sayfasına gidiş rotası
) {
    val nowPlaying by viewModel.nowPlaying.collectAsState()
    val popular by viewModel.popular.collectAsState()
    val topRated by viewModel.topRated.collectAsState()
    val upcoming by viewModel.upcoming.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        item {
            // Üst Bar: Sadece Profil ve Arama İkonları
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = stringResource(R.string.profile),
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { onProfileClick() }
                )

                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search),
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { onSearchClick() }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Dil dosyalarına bağlanan kategori satırları
            MovieCategoryRow(stringResource(R.string.now_playing), nowPlaying, onMovieClick)
            MovieCategoryRow(stringResource(R.string.popular_movies), popular, onMovieClick)
            MovieCategoryRow(stringResource(R.string.top_rated), topRated, onMovieClick)
            MovieCategoryRow(stringResource(R.string.upcoming), upcoming, onMovieClick)
        }
    }
}

@Composable
fun MovieCategoryRow(
    title: String,
    resource: Resource<MovieResponse>?,
    onMovieClick: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)) {
        Text(
            text = title, fontSize = 22.sp, fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        when (resource) {
            is Resource.Loading -> {
                Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }
            is Resource.Success -> {
                val movies = resource.data?.results ?: emptyList()
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(movies) { movie ->
                        Card(
                            modifier = Modifier
                                .width(140.dp)
                                .wrapContentHeight()
                                .clickable { onMovieClick(movie.id) },
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Column {
                                AsyncImage(
                                    model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                                    contentDescription = movie.title,
                                    modifier = Modifier.fillMaxWidth().height(200.dp),
                                    contentScale = ContentScale.Crop
                                )
                                Text(
                                    text = movie.title,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 14.sp, fontWeight = FontWeight.Medium, maxLines = 2,
                                    overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                }
            }
            is Resource.Error -> {
                val rawError = resource.message ?: ""

                // Daha güçlü ve esnek ağ hatası kontrolü (büyük/küçük harf duyarsız)
                val isNetworkError = rawError.contains("resolve host", ignoreCase = true) ||
                        rawError.contains("hostname", ignoreCase = true) ||
                        rawError.contains("failed to connect", ignoreCase = true) ||
                        rawError.contains("timeout", ignoreCase = true)

                val displayMessage = if (isNetworkError) {
                    stringResource(R.string.no_internet)
                } else {
                    rawError.ifEmpty { stringResource(R.string.error) }
                }

                Text(
                    text = displayMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            else -> {}
        }
    }
}