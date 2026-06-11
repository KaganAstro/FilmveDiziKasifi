package com.example.filmvedizikasifi.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class MovieUtilsTest {

    @Test
    fun `formatRating null deger geldiginde 0_0 dondurmelidir`() {
        // Hazırlık ve Eylem
        val result = MovieUtils.formatRating(null)

        // Doğrulama
        assertEquals("0.0", result)
    }

    @Test
    fun `formatRating kusuratli puani tek haneye yuvarlamalidir`() {
        val result = MovieUtils.formatRating(7.905)

        // Doğrulama
        assertEquals("7.9", result)
    }

    @Test
    fun `formatRating tam sayi geldiginde sonuna sifir eklemelidir`() {
        // Hazırlık ve Eylem
        val result = MovieUtils.formatRating(8.0)

        // Doğrulama
        assertEquals("8.0", result)
    }
}