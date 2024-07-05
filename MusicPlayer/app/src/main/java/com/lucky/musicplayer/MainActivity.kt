package com.lucky.musicplayer

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lucky.musicplayer.ui.theme.MusicPlayerTheme
import com.lucky.musicplayer.ui.screens.MusicPlayerScreen
import com.lucky.musicplayer.ui.screens.PlaylistScreen
import com.lucky.musicplayer.ui.screens.PlaylistDetailScreen

class MainActivity : ComponentActivity() {
    private lateinit var mediaPlayer: MediaPlayer
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        mediaPlayer = MediaPlayer.create(this, R.raw.memories)

        setContent {
            MusicPlayerTheme {
                val navController = rememberNavController()
                var isPlaying by remember { mutableStateOf(false) }
                var currentTime by remember { mutableStateOf(0) }
                val totalTime = mediaPlayer.duration

                DisposableEffect(Unit) {
                    val updateTime = object : Runnable {
                        override fun run() {
                            if (mediaPlayer.isPlaying) {
                                currentTime = mediaPlayer.currentPosition
                                handler.postDelayed(this, 1000)
                            }
                        }
                    }
                    if (isPlaying) {
                        handler.post(updateTime)
                    }
                    onDispose {
                        handler.removeCallbacks(updateTime)
                        mediaPlayer.release()
                    }
                }

                NavHost(navController = navController, startDestination = "music_player") {
                    composable("music_player") {
                        MusicPlayerScreen(
                            isPlaying = isPlaying,
                            currentTime = currentTime,
                            totalTime = totalTime,
                            onPlayPause = {
                                if (mediaPlayer.isPlaying) {
                                    mediaPlayer.pause()
                                } else {
                                    mediaPlayer.start()
                                }
                                isPlaying = !isPlaying
                            },
                            onNextPrevious = {
                                Toast.makeText(this@MainActivity, "No more songs", Toast.LENGTH_SHORT).show()
                            },
                            navController = navController
                        )
                    }
                    composable("playlist_screen") {
                        PlaylistScreen(
                            playlists = combinePlaylists(this@MainActivity),
                            onPlaylistClick = { playlist ->
                                navController.navigate("playlist_detail/${playlist.name}")
                            }
                        )
                    }
                    composable("playlist_detail/{playlistName}") { backStackEntry ->
                        val playlistName = backStackEntry.arguments?.getString("playlistName")
                        val playlist = combinePlaylists(this@MainActivity).find { it.name == playlistName }
                        playlist?.let {
                            PlaylistDetailScreen(playlist = it)
                        }
                    }
                }
            }
        }
    }
}
