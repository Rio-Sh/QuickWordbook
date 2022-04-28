/* (C)2022 Rio-Sh */
package com.io.github.rio_sh.quickwordbook.ui.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

/**
 * Stateful composable for Home Screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel,
    onAddFabClicked: () -> Unit,
    onCardsFabClicked: () -> Unit,
    onEditClicked: (wordId: Int) -> Unit,
) {
    val uiState by homeViewModel.uiState.collectAsState()

    HomeBody(
        uiState = uiState,
        onAddFabClicked = onAddFabClicked,
        onCardsFabClicked = onCardsFabClicked,
        onEditClicked = onEditClicked,
        onDeleteWord = { homeViewModel.deleteWord(it) }
    )
}
