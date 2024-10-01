package com.adyen.android.assignment

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

@Composable
fun MyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val darkColorTheme = darkColorScheme(
//        primary = colorResource(id = R.color.primary),
//        onBackground = colorResource(id = R.color.textColor),
//        onPrimary = colorResource(id = R.color.textColor),
//        background = colorResource(id = R.color.background),
//        surface = colorResource(id = R.color.on_background),
//        onSurface = colorResource(id = R.color.textColor),
    )
    MaterialTheme(
        colorScheme = darkColorTheme,
        content = content
    )
}