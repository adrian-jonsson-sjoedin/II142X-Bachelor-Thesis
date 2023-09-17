package com.sigma.stepcounter.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = VeryYellow,
    primaryVariant = DarkRed,
    secondary = MainRed,
    onPrimary = VeryYellow,
    onSecondary = VeryYellow,
    background = DarkGrey,
    surface = DarkGrey,

)

private val LightColorPalette = lightColors(
    primary = VeryYellow,
    primaryVariant = DarkRed,
    secondary = MainRed,
    onPrimary = DarkRed,
    background = BarelyGrey,
    surface = LightGrey,

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun StepCounterTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}