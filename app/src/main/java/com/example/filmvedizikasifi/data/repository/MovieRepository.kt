package com.example.filmvedizikasifi.data.repository

import com.example.filmvedizikasifi.data.Resource
import com.example.filmvedizikasifi.data.model.MovieDetail
import com.example.filmvedizikasifi.data.model.MovieResponse
import com.example.filmvedizikasifi.data.remote.TmdbApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val apiService: TmdbApiService
) {


    suspend fun getNowPlaying(): Resource<MovieResponse> {
        return try {
            Resource.Success(apiService.getNowPlayingMovies())
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Bağlantı hatası")
        }
    }

    suspend fun getPopular(): Resource<MovieResponse> {
        return try {
            Resource.Success(apiService.getPopularMovies())
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Bağlantı hatası")
        }
    }

    suspend fun getTopRated(): Resource<MovieResponse> {
        return try {
            Resource.Success(apiService.getTopRatedMovies())
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Bağlantı hatası")
        }
    }

    suspend fun getUpcoming(): Resource<MovieResponse> {
        return try {
            Resource.Success(apiService.getUpcomingMovies())
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Bağlantı hatası")
        }
    }

    suspend fun searchMovies(query: String, page: Int = 1): Resource<MovieResponse> {
        return try {
            Resource.Success(apiService.searchMovies(query = query, page = page))
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Bağlantı hatası")
        }
    }
    suspend fun getMovieDetails(movieId: Int): Resource<MovieDetail> {
        return try {
            Resource.Success(data = apiService.getMovieDetails(movieId = movieId))
        } catch (e: Exception) {
            Resource.Error(message = e.localizedMessage ?: "Bağlantı hatası")
        }
    }
}
