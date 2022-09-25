package com.apro.paraflight.design

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
  primary = Brand,
  onPrimary = Neutral10,
  primaryContainer = Neutral10,
  onPrimaryContainer = Neutral10,
  secondary = Success,
  onSecondary = Neutral10,
  secondaryContainer = Success,
  onSecondaryContainer = Neutral10,
  tertiary = Warning,
  onTertiary = Neutral10,
  tertiaryContainer = Warning,
  onTertiaryContainer = Neutral10,
  background = Neutral10,
  onBackground = Neutral99,
  surface = Neutral10,
  onSurface = Neutral99,
  error = Error,
  errorContainer = Error,
  onErrorContainer = Neutral10,
  outline = Brand,
)

private val LightColorScheme = lightColorScheme(
  primary = Brand,
  onPrimary = Neutral99,
  primaryContainer = Brand,
  onPrimaryContainer = Neutral99,
  secondary = Success,
  onSecondary = Neutral10,
  secondaryContainer = Success,
  onSecondaryContainer = Neutral10,
  tertiary = Warning,
  onTertiary = Neutral10,
  tertiaryContainer = Warning,
  onTertiaryContainer = Neutral10,
  background = Neutral99,
  onBackground = Neutral10,
  surface = Neutral99,
  onSurface = Neutral10,
  error = Error,
  errorContainer = Error,
  onErrorContainer = Neutral99,
  outline = Brand,
)

@Composable
fun ParaflightTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colorScheme = when {
    darkTheme -> DarkColorScheme
    else -> LightColorScheme
  }
  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    shapes = Shapes,
    content = content
  )
}

object ParaflightTheme {
  val colorScheme: ColorScheme
    @Composable
    get() = MaterialTheme.colorScheme

  val typography: Typography
    @Composable
    get() = MaterialTheme.typography

  val shapes: Shapes
    @Composable
    get() = MaterialTheme.shapes
}