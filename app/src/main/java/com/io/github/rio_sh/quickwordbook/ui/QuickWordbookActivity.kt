package com.io.github.rio_sh.quickwordbook.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.SideEffect
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.io.github.rio_sh.quickwordbook.ui.theme.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuickWordbookActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            // Update the status bars to be translucent
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = !isSystemInDarkTheme()
            val statusBarColor = if (!isSystemInDarkTheme()) md_theme_light_surfaceVariant else md_theme_dark_surfaceVariant
            SideEffect {
                systemUiController.setStatusBarColor(statusBarColor, darkIcons = useDarkIcons)
            }

            QuickWordbookApp()
        }
    }
}