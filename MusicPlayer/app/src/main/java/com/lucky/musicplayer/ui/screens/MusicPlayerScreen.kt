package com.lucky.musicplayer.ui.screens

import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lucky.musicplayer.R
import com.lucky.musicplayer.ui.components.CustomHeader
import com.lucky.musicplayer.ui.components.MusicPlayerControls
import com.lucky.musicplayer.ui.components.MusicProgressBar

@Composable
fun MusicPlayerScreen(
    isPlaying: Boolean,
    currentTime: Int,
    totalTime: Int,
    onPlayPause: () -> Unit,
    onNextPrevious: () -> Unit,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF00BB00),
                        Color(0xFF001900)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .padding(top = 20.dp)
        ) {
            CustomHeader(
                title = "Music",
                navController = navController
            )

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .padding(vertical = 15.dp, horizontal = 10.dp)
                    .background(Color.Transparent),
                shape = RoundedCornerShape(12.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.artist_art),
                    contentDescription = "Music Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(1.5f),
                    contentScale = ContentScale.FillBounds
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
                    .background(Color.Transparent)
                    .clip(RoundedCornerShape(50))
            ) {
                Text(
                    text = "Memories",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Black,
                    color = Color.White
                )
            }

            MusicProgressBar(
                currentTime = currentTime,
                totalTime = totalTime
            )

            MusicPlayerControls(
                isPlaying = isPlaying,
                onPlayPause = onPlayPause,
                onNext = onNextPrevious,
                onPrevious = onNextPrevious,
                onShuffle = { /* Implement shuffle functionality */ },
                onRepeat = { /* Implement repeat functionality */ },
                isShuffling = false,
                isRepeating = false
            )
        }
    }
}
