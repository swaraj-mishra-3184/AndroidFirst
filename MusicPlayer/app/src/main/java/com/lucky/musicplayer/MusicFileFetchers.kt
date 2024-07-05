package com.lucky.musicplayer

import Playlist
import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore

fun fetchDeviceMusicFiles(context: Context): List<Playlist> {
    val playlists = mutableListOf<Playlist>()

    val resolver = context.contentResolver
    val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    val projection = arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.ALBUM,
        MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.DURATION
    )
    val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"
    val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

    resolver.query(uri, projection, selection, null, sortOrder)?.use { cursor ->
        val idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
        val albumColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)
        val titleColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)

        var currentAlbum = ""
        var currentPlaylist: MutableList<String>? = null

        while (cursor.moveToNext()) {
            val album = cursor.getString(albumColumn)
            val title = cursor.getString(titleColumn)
            val id = cursor.getLong(idColumn)

            if (currentAlbum != album) {
                if (currentPlaylist != null) {
                    playlists.add(Playlist(currentAlbum, currentPlaylist))
                }
                currentAlbum = album
                currentPlaylist = mutableListOf()
            }

            val contentUri = ContentUris.withAppendedId(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                id
            ).toString()

            currentPlaylist?.add(contentUri)
        }

        if (currentPlaylist != null) {
            playlists.add(Playlist(currentAlbum, currentPlaylist))
        }
    }

    return playlists
}

fun fetchRawMusicFiles(context: Context): List<Playlist> {
    val rawMusicFiles = mutableListOf<Playlist>()
    val songIds = arrayOf(R.raw.memories)

    songIds.forEachIndexed { index, songId ->
        val resourceName = context.resources.getResourceEntryName(songId)
        val playlist = Playlist("Raw Playlist ${index + 1}", listOf(resourceName))
        rawMusicFiles.add(playlist)
    }

    return rawMusicFiles
}

fun combinePlaylists(context: Context): List<Playlist> {
    val deviceMusicFiles = fetchDeviceMusicFiles(context)
    val rawMusicFiles = fetchRawMusicFiles(context)

    return deviceMusicFiles + rawMusicFiles
}
