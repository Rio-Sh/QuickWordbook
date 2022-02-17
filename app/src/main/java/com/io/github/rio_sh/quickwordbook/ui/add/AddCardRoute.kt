package com.io.github.rio_sh.quickwordbook.ui.add

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun AddCardRoute(
    addCardViewModel: AddCardViewModel,
    onAddWord: () -> Unit,
    onBackClicked: () -> Unit,
) {
    val uiState by addCardViewModel.uiState.collectAsState()

    AddCardBody(
        uiState = uiState,
        onTranslateText = { addCardViewModel.translateText() },
        onSourceTextChanged = { addCardViewModel.changeSourceText(it) },
        onTargetTextChanged = { addCardViewModel.changeTargetText(it) },
        onAddButtonClicked = {
            addCardViewModel.addWord()
            onAddWord()
        },
        onBackClicked = { onBackClicked() },
        onTargetLanguageChanged = { addCardViewModel.changeTargetLanguage() },
        onToggleSwitch = { addCardViewModel.toggleSwitch(it) }
    )
}