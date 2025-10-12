package com.example.musicappexa.services

import com.example.musicappexa.models.Music
import retrofit2.http.GET
import retrofit2.http.Path

interface MusicService {

    @GET("albums")
    suspend fun getAllAlbums() : List<Music>

    @GET("albums/{id}")
    suspend fun getAlbumById(@Path("id") id : String) : Music
}