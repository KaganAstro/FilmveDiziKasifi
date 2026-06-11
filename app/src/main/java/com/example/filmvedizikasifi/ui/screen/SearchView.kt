package com.example.filmvedizikasifi.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(
    viewModel: SearchViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val searchState by viewModel.searchResults.collectAsState()
    val listState = rememberLazyListState()

    // Liste sonuna gelinip gelinmediğini anlayan sistem
    val isAtBottom by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            totalItems > 0 && lastVisibleItem >= totalItems - 1
        }
    }

    LaunchedEffect(query) {
        if (query.isNotEmpty()) {
            delay(500)
            viewModel.search(query)
        }
    }

    LaunchedEffect(isAtBottom) {
        if (isAtBottom && !viewModel.isFetching) {
            viewModel.loadNextPage()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(stringResource(R.string.search)) },
            trailingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (val resource = searchState) {
            is Resource.Loading -> {
                Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }
            is Resource.Success -> {
                val movies = resource.data?.results ?: emptyList()

                LazyColumn(
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(movies) { movie ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onMovieClick(movie.id) },
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                                AsyncImage(
                                    model = "https://image.tmdb.org/t/p/w92${movie.posterPath}",
                                    contentDescription = movie.title,
                                    modifier = Modifier.size(80.dp, 120.dp),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Column {
                                    Text(text = movie.title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = "⭐ ${movie.voteAverage}", fontSize = 14.sp, color = Color.Gray)
                                }
                            }
                        }
                    }

                    // Alta doğru kaydırırken API'den veri çekiliyorsa ufak bir yükleniyor ikonu göster
                    if (viewModel.isFetching) {
                        item {
                            Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(modifier = Modifier.size(30.dp), color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }
            }
            is Resource.Error -> {
                Text(text = resource.message ?: stringResource(R.string.error), color = MaterialTheme.colorScheme.error)
            }
            null -> {}
        }
    }
}