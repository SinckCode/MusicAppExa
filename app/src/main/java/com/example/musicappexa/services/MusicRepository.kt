package com.example.musicappexa.services

import com.example.musicappexa.models.Music
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class MusicRepository(
    private val api: MusicService = ServiceRetrofit.musicService
) {
    // Cache en memoria + lock para concurrencia
    private val mutex = Mutex()
    private var albumsCache: List<Music>? = null

    /** Devuelve álbumes desde cache si existe; si no, desde red y actualiza cache. */
    suspend fun getAlbums(forceRefresh: Boolean = false): List<Music> = mutex.withLock {
        if (!forceRefresh) albumsCache?.let { return it }
        val data = api.getAllAlbums()
        albumsCache = data
        data
    }

    /** Obtiene 1 álbum; intenta cache primero para evitar otra llamada. */
    suspend fun getAlbumById(id: String, preferCache: Boolean = true): Music {
        if (preferCache) {
            albumsCache?.firstOrNull { it.id == id }?.let { return it }
        }
        val item = api.getAlbumById(id)
        // sincroniza cache con el item recibido
        mutex.withLock {
            val current = (albumsCache ?: emptyList()).toMutableList()
            val idx = current.indexOfFirst { it.id == id }
            if (idx >= 0) current[idx] = item else current.add(item)
            albumsCache = current
        }
        return item
    }

    /** Variante segura que encapsula errores (útil si no usas try/catch en la UI). */
    sealed class RepoResult<out T> {
        data class Ok<T>(val data: T) : RepoResult<T>()
        data class Err(val message: String, val cause: Throwable? = null) : RepoResult<Nothing>()
    }

    suspend fun getAlbumsSafe(): RepoResult<List<Music>> = try {
        RepoResult.Ok(getAlbums())
    } catch (e: Exception) {
        RepoResult.Err(e.message ?: "Network error", e)
    }
}
