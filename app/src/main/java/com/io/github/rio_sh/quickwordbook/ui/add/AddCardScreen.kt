package com.io.github.rio_sh.quickwordbook.ui.add

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun AddCardBody(
    uiState: AddCardUiState,
    onTranslateText: () -> Unit,
    onSourceTextChanged:(String) -> Unit,
    onTargetTextChanged: (String) -> Unit,
    onAddButtonClicked: () -> Unit,
    onBackClicked: () -> Unit,
    onTargetLanguageChanged: () -> Unit,
) {
    // TODO implement AddCardBody
    Text(text = "This is AddCard Screen")
}