package com.example.filmvedizikasifi.data.di

import com.example.filmvedizikasifi.data.remote.TmdbApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.themoviedb.org/3/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val client = okhttp3.OkHttpClient.Builder().addInterceptor { chain ->
            val url = chain.request().url.newBuilder()

                .addQueryParameter("api_key", "b7f328d386e4c0442aaa438305523412")
                .build()

            val request = chain.request().newBuilder().url(url).addHeader("accept", "application/json").build()
            chain.proceed(request)
        }.build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideTmdbApiService(retrofit: Retrofit): TmdbApiService {
        return retrofit.create(TmdbApiService::class.java)
    }
}