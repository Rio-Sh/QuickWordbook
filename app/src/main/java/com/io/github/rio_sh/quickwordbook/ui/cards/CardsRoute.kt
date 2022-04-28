/* (C)2022 Rio-Sh */
package com.io.github.rio_sh.quickwordbook.ui.cards

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

/**
 * Stateful composable for Cards Screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardsRoute(
    cardsViewModel: CardsViewModel,
    onBackClicked: () -> Unit,
    onAddFabClicked: () -> Unit,
    onEditClicked: (wordId: Int) -> Unit,
) {
    val uiState by cardsViewModel.uiState.collectAsState()

    CardsBody(
        uiState = uiState,
        onBackClicked = onBackClicked,
        onAddFabClicked = onAddFabClicked,
        onEditClicked = onEditClicked,
        onDeleteWord = { cardsViewModel.deleteWord(it) },
        toggleCardsExpand = { cardsViewModel.toggleCardsExpand(it) },
        onDeleteAllWords = { cardsViewModel.deleteAllWords() }
    )
}
