package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

enum class BibleThemeMode {
    SANCTUARY_DARK,
    WARM_SEPIA,
    CATHEDRAL_LIGHT
}

private val DarkSanctuaryScheme = darkColorScheme(
    primary = GoldPrimary,
    onPrimary = Color(0xFF1E1700),
    primaryContainer = Color(0xFF3A2E00),
    onPrimaryContainer = GoldLight,
    secondary = AmberGlow,
    onSecondary = Color(0xFF221100),
    background = SanctuaryBackground,
    onBackground = SanctuaryTextPrimary,
    surface = SanctuarySurface,
    onSurface = SanctuaryTextPrimary,
    surfaceVariant = SanctuaryCard,
    onSurfaceVariant = SanctuaryTextSecondary,
    outline = SanctuaryCardBorder
)

private val WarmSepiaScheme = lightColorScheme(
    primary = Color(0xFF9E6E27),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFF1DEB9),
    onPrimaryContainer = Color(0xFF332003),
    secondary = Color(0xFF875727),
    onSecondary = Color.White,
    background = SepiaBackground,
    onBackground = SepiaTextPrimary,
    surface = SepiaSurface,
    onSurface = SepiaTextPrimary,
    surfaceVariant = SepiaCard,
    onSurfaceVariant = SepiaTextSecondary,
    outline = SepiaCardBorder
)

private val CathedralLightScheme = lightColorScheme(
    primary = GoldDark,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFBEBC7),
    onPrimaryContainer = Color(0xFF2D1F00),
    secondary = AmberGlow,
    onSecondary = Color.White,
    background = LightBackground,
    onBackground = LightTextPrimary,
    surface = LightSurface,
    onSurface = LightTextPrimary,
    surfaceVariant = LightCard,
    onSurfaceVariant = LightTextSecondary,
    outline = LightCardBorder
)

@Composable
fun HolyBibleTheme(
    themeMode: BibleThemeMode = BibleThemeMode.SANCTUARY_DARK,
    content: @Composable () -> Unit
) {
    val colorScheme = when (themeMode) {
        BibleThemeMode.SANCTUARY_DARK -> DarkSanctuaryScheme
        BibleThemeMode.WARM_SEPIA -> WarmSepiaScheme
        BibleThemeMode.CATHEDRAL_LIGHT -> CathedralLightScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
