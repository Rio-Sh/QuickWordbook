package com.io.github.rio_sh.quickwordbook.ui

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.io.github.rio_sh.quickwordbook.ui.theme.QuickWordbookTheme

@Composable
fun QuickWordbookApp() {
    QuickWordbookTheme {
        ProvideWindowInsets {
            val navController = rememberNavController()
            NavHost(navController = navController, modifier = Modifier.statusBarsPadding())
        }
    }
}