package com.example.filmvedizikasifi.data.model

import com.google.gson.annotations.SerializedName

data class MovieDetail(
    val id: Int,
    val title: String?,
    val overview: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("poster_path") val posterPath: String?,
    val genres: List<Genre>?
)

data class Genre(
    val id: Int,
    val name: String
)