package com.example.filmvedizikasifi.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmvedizikasifi.data.Resource
import com.example.filmvedizikasifi.data.model.MovieDetail
import com.example.filmvedizikasifi.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movieDetail = MutableStateFlow<Resource<MovieDetail>?>(null)
    val movieDetail = _movieDetail.asStateFlow()

    fun getDetail(movieId: Int) {
        viewModelScope.launch {
            _movieDetail.value = Resource.Loading()
            _movieDetail.value = repository.getMovieDetails(movieId)
        }
    }
}