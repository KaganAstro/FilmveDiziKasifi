package com.example.filmvedizikasifi.data.remote

import com.example.filmvedizikasifi.data.model.MovieDetail
import com.example.filmvedizikasifi.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.Locale

// Telefonun mevcut dilini (Örn: "tr-TR" veya "en-US") otomatik alan yardımcı fonksiyonumuz
fun getDeviceLanguage(): String {
    return Locale.getDefault().toLanguageTag()
}

interface TmdbApiService {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String = getDeviceLanguage(),
        @Query("page") page: Int = 1,
        @Query(value = "region") region: String = "TR"
    ): MovieResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = getDeviceLanguage(),
        @Query("page") page: Int = 1
    ): MovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language: String = getDeviceLanguage(),
        @Query("page") page: Int = 1
    ): MovieResponse

    @GET(value = "movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query(value = "language") language: String = getDeviceLanguage(),
        @Query(value = "page") page: Int = 1,
        @Query(value = "region") region: String = "US"
    ): MovieResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("language") language: String = getDeviceLanguage(),
        @Query("page") page: Int = 1
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = getDeviceLanguage()
    ): MovieDetail
}