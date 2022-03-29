package com.io.github.rio_sh.quickwordbook.ui

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.io.github.rio_sh.quickwordbook.ui.theme.QuickWordbookTheme

@Composable
fun QuickWordbookApp() {
    QuickWordbookTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, modifier = Modifier.statusBarsPadding())
    }
}