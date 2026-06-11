package com.example.filmvedizikasifi.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmvedizikasifi.data.Resource
import com.example.filmvedizikasifi.data.model.MovieDto
import com.example.filmvedizikasifi.data.model.MovieResponse
import com.example.filmvedizikasifi.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _searchResults = MutableStateFlow<Resource<MovieResponse>?>(null)
    val searchResults = _searchResults.asStateFlow()

    private var currentPage = 1
    private var currentQuery = ""
    var isFetching = false

    private var currentMoviesList = mutableListOf<MovieDto>()

    fun search(query: String) {
        if (query.isBlank()) return
        currentQuery = query
        currentPage = 1
        currentMoviesList.clear()
        _searchResults.value = Resource.Loading()
        fetchMovies()
    }

    fun loadNextPage() {
        if (isFetching || currentQuery.isBlank()) return
        currentPage++
        fetchMovies()
    }

    private fun fetchMovies() {
        isFetching = true
        viewModelScope.launch {
            val result = repository.searchMovies(currentQuery, currentPage)

            if (result is Resource.Success) {
                // Yeni filmleri eski listenin üstüne ekle
                val newMovies = result.data?.results ?: emptyList()
                currentMoviesList.addAll(newMovies)

                val updatedList = ArrayList(currentMoviesList)
                val newResponse = result.data?.copy(results = updatedList)

                if (newResponse != null) {
                    _searchResults.value = Resource.Success(newResponse)
                }
            } else if (currentPage == 1) {
                _searchResults.value = result
            }
            isFetching = false
        }
    }
}