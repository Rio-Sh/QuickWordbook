package com.io.github.rio_sh.quickwordbook.ui.cards

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

/**
 * Stateful composable for Cards Screen
 */
@Composable
fun CardsRoute(
    cardsViewModel: CardsViewModel,
    onBackClicked: () -> Unit,
    onEditClicked: (wordId: Int) -> Unit,
) {
    val uiState by cardsViewModel.uiState.collectAsState()

    CardsBody(
        uiState = uiState,
        onBackClicked = onBackClicked,
        onEditClicked = onEditClicked,
        onDeleteWord = { cardsViewModel.deleteWord(it) },
        expandOrCloseCards = { cardsViewModel.expandOrCloseCards() },
        onDeleteAllWords = { cardsViewModel.deleteAllWords() }
    )
}