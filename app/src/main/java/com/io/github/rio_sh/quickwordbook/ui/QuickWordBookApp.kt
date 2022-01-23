package com.io.github.rio_sh.quickwordbook.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.io.github.rio_sh.quickwordbook.ui.theme.QuickWordbookTheme

@Composable
fun QuickWordBookApp() {
    QuickWordbookTheme {
        val navController = rememberNavController()
        Scaffold { innerPadding ->
            NavHost(navController = navController, modifier = Modifier.padding(innerPadding))
        }
    }
}