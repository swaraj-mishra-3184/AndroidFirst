package com.lucky.musicplayer.ui.screens

import Playlist
import android.content.Context
import android.provider.MediaStore
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lucky.musicplayer.R

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun PlaylistScreen(playlists: List<Playlist>, onPlaylistClick: (Playlist) -> Unit) {
    val context = LocalContext.current
    val musicFiles = remember(context) {
        mutableStateOf(fetchMusicFiles(context))
    }

    LazyColumn {
        item {
            Surface(Modifier.fillMaxSize()) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(R.string.device_music_files),
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    MusicFilesList(musicFiles.value)
                }
            }
        }
        items(playlists) { playlist ->
            PlaylistItem(playlist, onPlaylistClick)
        }
    }
}

@Composable
private fun MusicFilesList(musicFiles: List<String>) {
    Card(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = Color.White
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyColumn(Modifier.padding(8.dp)) {
            items(musicFiles) { fileName ->
                Text(
                    text = fileName,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun PlaylistItem(playlist: Playlist, onPlaylistClick: (Playlist) -> Unit) {
    Text(
        text = playlist.name,
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onPlaylistClick(playlist) }
    )
}

fun fetchMusicFiles(context: Context): List<String> {
    val musicFiles = mutableListOf<String>()
    val projection = arrayOf(MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME)
    val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"
    val cursor = context.contentResolver.query(
        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
        projection,
        selection,
        null,
        null
    )

    cursor?.use { cursor ->
        val displayNameColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)
        while (cursor.moveToNext()) {
            val displayName = cursor.getString(displayNameColumn)
            musicFiles.add(displayName)
        }
    }

    val rawResources = context.resources.assets?.list("") ?: emptyArray()
    for (resource in rawResources) {
        if (resource.endsWith(".mp3")) {
            musicFiles.add(resource)
        }
    }

    return musicFiles
}
