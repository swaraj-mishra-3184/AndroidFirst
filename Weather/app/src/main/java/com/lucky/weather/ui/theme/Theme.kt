package com.lucky.weather.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun WeatherTheme(isDarkTheme: Boolean = false, content: @Composable () -> Unit) {
    val colors = if (isDarkTheme) {
        darkColorScheme(
            primary = Color(0xFFBB86FC),
            primaryContainer = Color(0xFF3700B3),
            onPrimary = Color.Black,
            secondary = Color(0xFF03DAC6),
            secondaryContainer = Color(0xFF03DAC6),
            onSecondary = Color.Black,
            background = Color.Black,
            surface = Color.DarkGray,
            onBackground = Color.White,
            onSurface = Color.White
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF6200EE),
            primaryContainer = Color(0xFFBB86FC),
            onPrimary = Color.White,
            secondary = Color(0xFF03DAC6),
            secondaryContainer = Color(0xFF018786),
            onSecondary = Color.Black,
            background = Color(0xFFFFFFFF),
            surface = Color.LightGray,
            onBackground = Color.Black,
            onSurface = Color.Black
        )
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content,
        shapes = MaterialTheme.shapes
    )
}
