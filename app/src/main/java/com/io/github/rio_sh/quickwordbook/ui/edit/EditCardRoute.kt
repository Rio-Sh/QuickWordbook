package com.io.github.rio_sh.quickwordbook.ui.edit

import androidx.compose.runtime.*

@Composable
fun EditCardRoute(
    editCardViewModel: EditCardViewModel,
    wordId: Int,
    onEditWordDone: () -> Unit,
    onBackClicked: () -> Unit,
) {
    val uiSate by editCardViewModel.uiState.collectAsState()
    LaunchedEffect(key1 = true) {
        editCardViewModel.setWord(wordId = wordId)
    }

    EditCardBody(
        uiState = uiSate,
        onTranslateText = { editCardViewModel.translateText() },
        onSourceTextChanged = { editCardViewModel.changeSourceText(it) },
        onTargetTextChanged = { editCardViewModel.changeTargetText(it) },
        onTargetLanguageChanged = { editCardViewModel.changeTargetLanguage() },
        onToggleSwitch = { editCardViewModel.toggleSwitch(it) },
        onEditButtonClicked = {
            editCardViewModel.upDateWord()
            onEditWordDone()
        },
        onBackClicked = { onBackClicked() }
    )

}