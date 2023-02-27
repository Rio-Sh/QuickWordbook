/* (C)2022 Rio-Sh */
package com.io.github.rio_sh.quickwordbook.ui.edit

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.io.github.rio_sh.quickwordbook.R
import com.io.github.rio_sh.quickwordbook.ui.common.TranslateCard
import com.io.github.rio_sh.quickwordbook.ui.theme.QuickWordbookTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCardBody(
    uiState: EditCardUiState,
    onIdentifyText: () -> Unit,
    onTranslateText: () -> Unit,
    onSourceTextChanged: (String) -> Unit,
    onTargetTextChanged: (String) -> Unit,
    onTargetLanguageChanged: () -> Unit,
    onToggleSwitch: (Boolean) -> Unit,
    onEditButtonClicked: () -> Unit,
    onBackClicked: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            Row(horizontalArrangement = Arrangement.Start) {
                IconButton(onClick = { onBackClicked() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                    )
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = uiState.currentMessage.stringId),
                color = MaterialTheme.colorScheme.error
            )
            TranslateCard(
                sourceText = uiState.sourceText,
                targetText = uiState.targetText,
                isTargetTextLoading = uiState.isTargetTextLoading,
                targetLanguage = uiState.targetLanguage,
                isSwitchChecked = uiState.isSwitchChecked,
                onSourceTextChanged = onSourceTextChanged,
                onTargetTextChanged = onTargetTextChanged,
                onIdentifyText = onIdentifyText,
                onTranslateText = onTranslateText,
                onTargetLanguageChanged = onTargetLanguageChanged,
                onToggleSwitch = onToggleSwitch,
                onDoneButtonClicked = onEditButtonClicked
            ) {
                Text(stringResource(R.string.edit))
            }
            Spacer(modifier = Modifier.padding(32.dp))
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun EditCardScreenPreview() {
    QuickWordbookTheme {
        EditCardBody(
            uiState = EditCardUiState(),
            onIdentifyText = {},
            onTranslateText = {},
            onSourceTextChanged = {},
            onTargetTextChanged = {},
            onTargetLanguageChanged = {},
            onToggleSwitch = {},
            onEditButtonClicked = {},
            onBackClicked = {}
        )
    }
}
