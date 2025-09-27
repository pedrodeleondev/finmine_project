package com.dappsm.finmine_project.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.dappsm.ui_core.theme.AppTypographyPoppins
import com.dappsm.ui_core.theme.Poppins


private val DarkColorScheme = darkColorScheme(
    primary = BeigeRosa,
    primaryContainer = BeigeRosa,
    secondary = GrisOscuro,
    tertiary = Naranjo,
    background = GrisOscuro,
    surface = GrisOscuro,
    error = Rojo,
    surfaceContainer = BeigeRosa,

    onPrimary = GrisOscuro,
    onPrimaryContainer = GrisOscuro,
    onSecondary = GrisOscuro,
    onBackground = BeigeRosa,
    onSurface = BeigeRosa,
    onError = GrisOscuro
)

private val LightColorScheme = lightColorScheme(
    primary = MoradoMedio,
    primaryContainer = MoradoOscuro,
    secondary = MoradoLigero,
    tertiary = Naranja,
    background = Blanco,
    surface = Blanco,
    error = Rojo,
    onErrorContainer = Verde,
    surfaceContainer = gris,

    onPrimary = Blanco,
    onPrimaryContainer = Blanco,
    onSecondary = Blanco,
    onBackground = MoradoOscuro,
    onSurface = MoradoOscuro,
    onError = Blanco
)


@Composable
fun Finmine_projectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypographyPoppins,
        content = content
    )

}