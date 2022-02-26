package com.io.github.rio_sh.quickwordbook.ui.add

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun AddCardBody(
    uiState: AddCardUiState,
    onTranslateText: () -> Unit,
    onSourceTextChanged: (String) -> Unit,
    onTargetTextChanged: (String) -> Unit,
    onAddButtonClicked: () -> Unit,
    onBackClicked: () -> Unit,
    onTargetLanguageChanged: () -> Unit,
    onToggleSwitch: (Boolean) -> Unit,
) {
    Scaffold(
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
        containerColor = MaterialTheme.colorScheme.surfaceVariant
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
                onTranslateText = onTranslateText,
                onTargetLanguageChanged = onTargetLanguageChanged,
                onToggleSwitch = onToggleSwitch,
                onDoneButtonClicked = onAddButtonClicked
            ) {
                Text(stringResource(R.string.add))
            }
            Spacer(modifier = Modifier.padding(32.dp))
        }
    }
    
}


@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun AddCardScreenPreview() {
    QuickWordbookTheme {
        AddCardBody(
            uiState = AddCardUiState(),
            onTranslateText = {},
            onSourceTextChanged = {},
            onTargetTextChanged = {},
            onAddButtonClicked = {},
            onBackClicked = {},
            onTargetLanguageChanged = {},
            onToggleSwitch = {},
        )
    }
}