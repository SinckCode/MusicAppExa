package com.example.musicappexa.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceRetrofit {
    private const val BASE_URL = "https://music.juanfrausto.com/api/"

    val musicService: MusicService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // con “/” final
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(25, TimeUnit.SECONDS)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MusicService::class.java)
    }
}
