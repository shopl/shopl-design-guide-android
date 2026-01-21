package com.shopl.sdg.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography

private val DarkColorScheme = darkColorScheme(
    primary = SDGColor.Neutral50,
    secondary = SDGColor.Neutral700,
    tertiary = SDGColor.Neutral0
)

private val LightColorScheme = lightColorScheme(
    primary = SDGColor.Neutral700,
    secondary = SDGColor.Neutral0,
    tertiary = SDGColor.Neutral50
)

private val Typography = Typography(
    titleLarge = SDGTypography.Title1R.style,
    titleMedium = SDGTypography.Title2R.style,
    bodyLarge = SDGTypography.Body1R.style,
    bodyMedium = SDGTypography.Body2R.style,
    bodySmall = SDGTypography.Body3R.style,
    labelLarge = SDGTypography.Body2R.style,
    labelMedium = SDGTypography.Body3R.style,
    labelSmall = SDGTypography.Body4R.style,
)

@Composable
fun ShoplDesignGuideTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
