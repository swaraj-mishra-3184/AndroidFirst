package com.lucky.musicplayer

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.twotone.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lucky.musicplayer.ui.theme.MusicPlayerTheme
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    private lateinit var mediaPlayer: MediaPlayer
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        mediaPlayer = MediaPlayer.create(this, R.raw.memories)

        setContent {
            MusicPlayerTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                ) {
                    var isPlaying by remember { mutableStateOf(false) }
                    var currentTime by remember { mutableStateOf(0) }
                    val totalTime = mediaPlayer.duration

                    LaunchedEffect(isPlaying) {
                        if (isPlaying) {
                            handler.postDelayed(object : Runnable {
                                override fun run() {
                                    if (mediaPlayer.isPlaying) {
                                        currentTime = mediaPlayer.currentPosition
                                        handler.postDelayed(this, 1000)
                                    }
                                }
                            }, 1000)
                        } else {
                            handler.removeCallbacksAndMessages(null)
                        }
                    }

                    MusicPlayerScreen(
                        mediaPlayer = mediaPlayer,
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
                            Toast.makeText(this, "No more songs", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        handler.removeCallbacksAndMessages(null)
    }
}

@Composable
fun MusicPlayerScreen(
    mediaPlayer: MediaPlayer,
    isPlaying: Boolean,
    currentTime: Int,
    totalTime: Int,
    onPlayPause: () -> Unit,
    onNextPrevious: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF00BB00), // Green
                        Color(0xFF00A800), // Slightly darker green
                        Color(0xFF009C00), // Slightly darker green
                        Color(0xFF008F00), // Slightly darker green
                        Color(0xFF008300), // Slightly darker green
                        Color(0xFF006D00), // Slightly darker green
                        Color(0xFF005200), // Slightly darker green
                        Color(0xFF004C00), // Slightly darker green
                        Color(0xFF003300), // Slightly darker green
                        Color(0xFF001900)  // Very dark green
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
            CustomHeader(title = "Music",
                button1Icon = Icons.Default.KeyboardArrowDown,
                button2Icon = Icons.Default.Menu)

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .padding(vertical = 15.dp)
                    .background(Color.Gray)
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

            Box(modifier = Modifier.fillMaxWidth()
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .padding(top = 15.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CustomPreviousTrackButton(onNextPrevious)
                CustomPlayPauseButton(isPlaying = isPlaying, onClick = onPlayPause)
                CustomNextTrackButton(onNextPrevious)
            }
        }
    }
}

@Composable
fun CustomPlayPauseButton(
    isPlaying: Boolean,
    onClick: () -> Unit,
    buttonSize: Dp = 60.dp,
    buttonColor: Color = Color.LightGray,
    shadowElevation: Dp = 8.dp,
    shape: Shape = RoundedCornerShape(50)
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(buttonSize)
            .shadow(shadowElevation, shape)
            .clip(shape)
            .background(buttonColor),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            contentColor = Color.White
        ),
        shape = shape,
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(
            imageVector = if (isPlaying) Icons.TwoTone.Close else Icons.Default.PlayArrow,
            contentDescription = if (isPlaying) "Pause" else "Play",
            tint = Color.Black,
            modifier = Modifier
                .size(buttonSize * 0.6f)
        )
    }
}

@Composable
fun CustomPreviousTrackButton(
    onClick: () -> Unit,
    buttonSize: Dp = 60.dp,
    buttonColor: Color = Color.LightGray,
    shadowElevation: Dp = 8.dp,
    shape: Shape = RoundedCornerShape(50)
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(buttonSize)
            .shadow(shadowElevation, shape)
            .clip(shape)
            .background(buttonColor),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            contentColor = Color.White
        ),
        shape = shape,
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Previous",
            tint = Color.Black,
            modifier = Modifier
                .size(buttonSize * 0.6f)
        )
    }
}

@Composable
fun CustomNextTrackButton(
    onClick: () -> Unit,
    buttonSize: Dp = 60.dp,
    buttonColor: Color = Color.LightGray,
    shadowElevation: Dp = 8.dp,
    shape: Shape = RoundedCornerShape(50)
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(buttonSize)
            .shadow(shadowElevation, shape)
            .clip(shape)
            .background(buttonColor),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            contentColor = Color.White
        ),
        shape = shape,
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "Next",
            tint = Color.Black,
            modifier = Modifier
                .size(buttonSize * 0.6f)
        )
    }
}

@Composable
fun MusicProgressBar(
    currentTime: Int,
    totalTime: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LinearProgressIndicator(
            progress = if (totalTime > 0) currentTime / totalTime.toFloat() else 0f,
            color = Color.LightGray,
            trackColor = Color(0xFF006600),
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formatTime(currentTime),
                color = Color.White,
                fontSize = 14.sp
            )

            Text(
                text = formatTime(totalTime),
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun CustomHeader(
    title: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFF004C00),
    textColor: Color = Color.White,
    button1Icon: ImageVector? = null,
    button1OnClick: () -> Unit = {},
    button2Icon: ImageVector? = null,
    button2OnClick: () -> Unit = {},
    shape: Shape = RoundedCornerShape(50)
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .alpha(1.2f)
            .padding(top = 10.dp, start = 5.dp, end = 5.dp, bottom = 10.dp)
            .clip(RoundedCornerShape(50))
            .clip(RoundedCornerShape(15.dp)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (button1Icon != null) {
            IconButton(
                onClick = button1OnClick,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = button1Icon,
                    contentDescription = null,
                    tint = textColor
                )
            }
        } else {
            Spacer(modifier = Modifier.width(48.dp))
        }

        Text(
            text = title,
            color = textColor,
            fontSize = 20.sp
        )

        if (button2Icon != null) {
            IconButton(
                onClick = button2OnClick,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = button2Icon,
                    contentDescription = null,
                    tint = textColor
                )
            }
        } else {
            Spacer(modifier = Modifier.width(48.dp))
        }
    }
}

private fun formatTime(milliseconds: Int): String {
    val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds.toLong())
    val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds.toLong()) % 60
    return String.format("%02d:%02d", minutes, seconds)
}
