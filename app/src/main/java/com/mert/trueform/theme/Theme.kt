package com.mert.trueform.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val DarkNavy = Color(0xFF0F0C29)
val DeepPurple = Color(0xFF302B63)
val NeonYellow = Color(0xFFF8FFAE)
val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)
val VeryDarkGrey = Color(0xFF121212)
val HotPink = Color(0xFFFF4081)

val FitnessTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        color = NeonYellow
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = Color.White
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        color = Color.Gray
    )
)

private val DarkColorScheme = darkColorScheme(
    primary = White,
    secondary = DeepPurple,
    background = DarkNavy,
    surface = VeryDarkGrey,
    error = HotPink,
    onPrimary = White,
    onSecondary = White,
    onBackground = NeonYellow,
    onSurface = NeonYellow,
    onError = Black
)

@Composable
fun FitnessAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = FitnessTypography,
        content = content
    )
}