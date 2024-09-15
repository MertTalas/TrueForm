package com.mert.trueform.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val BackgroundWhite = Color(0xFFFFFFFF)
val SurfaceLightGray = Color(0xFFD4D4D4)
val PrimaryTextBlack = Color(0xFF000000)
val SecondaryTextGray = Color(0xFF757575)
val ErrorRed = Color(0xFFD32F2F)

val FitnessTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        color = PrimaryTextBlack
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = PrimaryTextBlack
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        color = SecondaryTextGray
    )
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryTextBlack,
    secondary = SecondaryTextGray,
    background = BackgroundWhite,
    surface = SurfaceLightGray,
    error = ErrorRed,
    onPrimary = BackgroundWhite,
    onSecondary = BackgroundWhite,
    onBackground = PrimaryTextBlack,
    onSurface = PrimaryTextBlack,
    onError = BackgroundWhite
)

@Composable
fun FitnessAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = FitnessTypography,
        content = content
    )
}