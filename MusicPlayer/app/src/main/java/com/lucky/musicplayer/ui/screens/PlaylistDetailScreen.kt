package com.lucky.musicplayer.ui.screens

import Playlist
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PlaylistDetailScreen(playlist: Playlist) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = playlist.name, modifier = Modifier.padding(8.dp))
        LazyColumn {
            items(playlist.songs) { song ->
                Text(
                    text = song,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }
    }
}
