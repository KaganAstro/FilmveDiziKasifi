package com.example.filmvedizikasifi.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmvedizikasifi.data.repository.MovieRepository
import com.example.filmvedizikasifi.data.Resource
import com.example.filmvedizikasifi.data.model.MovieResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _nowPlaying = MutableStateFlow<Resource<MovieResponse>?>(null)
    val nowPlaying = _nowPlaying.asStateFlow()

    private val _popular = MutableStateFlow<Resource<MovieResponse>?>(null)
    val popular = _popular.asStateFlow()

    private val _topRated = MutableStateFlow<Resource<MovieResponse>?>(null)
    val topRated = _topRated.asStateFlow()

    private val _upcoming = MutableStateFlow<Resource<MovieResponse>?>(null)
    val upcoming = _upcoming.asStateFlow()

    init {
        fetchAllMovies()
    }

    private fun fetchAllMovies() {
        viewModelScope.launch {
            _nowPlaying.value = Resource.Loading()
            _nowPlaying.value = repository.getNowPlaying()
        }
        viewModelScope.launch {
            _popular.value = Resource.Loading()
            _popular.value = repository.getPopular()
        }
        viewModelScope.launch {
            _topRated.value = Resource.Loading()
            _topRated.value = repository.getTopRated()
        }
        viewModelScope.launch {
            _upcoming.value = Resource.Loading()
            _upcoming.value = repository.getUpcoming()
        }
    }
}