package com.lucky.musicplayer.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.data.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MusicPlayerControls(
    isPlaying: Boolean,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onShuffle: () -> Unit,
    onRepeat: () -> Unit,
    isShuffling: Boolean,
    isRepeating: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(onClick = onShuffle) {
            Icon(
                painter = if (isPlaying) painterResource(id = com.lucky.musicplayer.R.drawable.shuffle )
                else painterResource(id = com.lucky.musicplayer.R.drawable.shuffle ),
                contentDescription = "Shuffle",
                tint = Color.LightGray,
                modifier = Modifier.clip(shape = CircleShape)
                    .padding(5.dp)
                    .clip(shape = CircleShape)
            )
        }
        IconButton(onClick = onPrevious) {
            Icon(
                //painter = painterResource(id = com.lucky.musicplayer.R.drawable.ic_previous),
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Previous",
                tint = Color.White,
                modifier = Modifier.clip(shape = CircleShape).fillMaxWidth().padding(5.dp)
//                    .shadow(elevation = 5.dp,
//                        ambientColor = Color.White,
//                        spotColor = Color.White )
            )
        }
        IconButton(onClick = onPlayPause) {
            Icon(
                painter = if (isPlaying) painterResource(id = com.lucky.musicplayer.R.drawable.ic_pause )
                            else painterResource(id = com.lucky.musicplayer.R.drawable.ic_play ),
                //imageVector = if (isPlaying) Icons.Filled.FavoriteBorder else Icons.Filled.PlayArrow,
                contentDescription = "Play/Pause",
                tint = Color.White,
                modifier = Modifier.clip(shape = CircleShape)
            )
        }
        IconButton(onClick = onNext) {
            Icon(
                //painter = painterResource(id = com.lucky.musicplayer.R.drawable.ic_next ),
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "Next",
                tint = Color.LightGray,
                modifier = Modifier.clip(shape = CircleShape)
            )
        }
        IconButton(onClick = onRepeat) {
            Icon(
                painter = if (isPlaying) painterResource(id = com.lucky.musicplayer.R.drawable.repeat )
                else painterResource(id = com.lucky.musicplayer.R.drawable.repeat ),
                contentDescription = "Repeat",
                tint = Color.LightGray,
                modifier = Modifier.clip(shape = CircleShape)
            )
        }
    }
}
