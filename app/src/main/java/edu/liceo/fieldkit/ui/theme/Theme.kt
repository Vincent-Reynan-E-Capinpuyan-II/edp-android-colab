package edu.liceo.fieldkit.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Self-contained theme for LiceoFieldKit so it does not depend on
// com.example.capinpuyan.ui.theme. Only the function name matters to the
// GIVEN MainActivity.

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF9C7C00),
    secondary = Color(0xFF6B5C1E),
    tertiary = Color(0xFF7A5900)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6B5C00),
    secondary = Color(0xFF7A6A1E),
    tertiary = Color(0xFF8A6700)
)

@Composable
fun LiceoFieldKitTheme(
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
        typography = Typography(),
        content = content
    )
}