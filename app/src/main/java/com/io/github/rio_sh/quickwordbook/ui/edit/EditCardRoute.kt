package com.io.github.rio_sh.quickwordbook.ui.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun EditCardRoute(
    editCardViewModel: EditCardViewModel,
    wordId: Int,
    onEditWordDone:() -> Unit,
    onBackClicked: () -> Unit,
) {
    val uiSate by editCardViewModel.uiState.collectAsState()
    editCardViewModel.setWordId(wordId = wordId)

    // TODO EditCard Body
}