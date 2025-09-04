package com.example.memora.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.memora.presentation.viewmodel.ThemeViewModel


data class AppTheme(
    val primary: androidx.compose.ui.graphics.Color,
    val onPrimary: androidx.compose.ui.graphics.Color,
    val background: androidx.compose.ui.graphics.Color
)

val themeOptions = listOf(
    AppTheme(primaryLight, onPrimaryLight, backgroundLight),
    AppTheme(
        primaryLightMediumContrast,
        onPrimaryLightMediumContrast,
        backgroundLightMediumContrast
    ),
    AppTheme(primaryLightHighContrast, onPrimaryLightHighContrast, backgroundLightHighContrast),
    AppTheme(primaryDark, onPrimaryDark, backgroundDark),
    AppTheme(primaryDarkMediumContrast, onPrimaryDarkMediumContrast, backgroundDarkMediumContrast),
    AppTheme(primaryDarkHighContrast, onPrimaryDarkHighContrast, backgroundDarkHighContrast),
)

@Composable
fun MemoraTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    themeViewModel: ThemeViewModel = viewModel(),
    content: @Composable () -> Unit
) {
    val selectedThemeIndex by themeViewModel.selectedThemeIndex.collectAsState()
    val selectedTheme = themeOptions[selectedThemeIndex]

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        else -> lightColorScheme(
            primary = selectedTheme.primary,
            onPrimary = selectedTheme.onPrimary,
            background = backgroundLight,
            surface = backgroundLight,
            onBackground = onBackgroundLight,
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}