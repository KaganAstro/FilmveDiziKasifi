package com.example.filmvedizikasifi.utils

import java.util.Locale

object MovieUtils {
    // API'den gelen küsuratlı puanları tek haneye yuvarlar
    fun formatRating(rating: Double?): String {
        if (rating == null) return "0.0"
        return String.format(Locale.US, "%.1f", rating)
    }
}