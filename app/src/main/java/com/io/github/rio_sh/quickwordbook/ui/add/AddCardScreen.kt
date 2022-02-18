package com.io.github.rio_sh.quickwordbook.ui.add

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding
import com.io.github.rio_sh.quickwordbook.network.Languages
import com.io.github.rio_sh.quickwordbook.ui.theme.QuickWordbookTheme
import com.io.github.rio_sh.quickwordbook.ui.theme.Shapes

@Composable
fun AddCardBody(
    uiState: AddCardUiState,
    onTranslateText: () -> Unit,
    onSourceTextChanged:(String) -> Unit,
    onTargetTextChanged: (String) -> Unit,
    onAddButtonClicked: () -> Unit,
    onBackClicked: () -> Unit,
    onTargetLanguageChanged: () -> Unit,
    onToggleSwitch: (Boolean) -> Unit,
) {
    // TODO implement AddCardBody
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.statusBarsPadding(),
                horizontalArrangement = Arrangement.Start
            ){
                IconButton(onClick = { onBackClicked() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        backgroundColor = MaterialTheme.colorScheme.surface
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = Shapes.medium,
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Column(modifier = Modifier
                    .padding(16.dp)
                    .width(IntrinsicSize.Max)
                ) {
                    TranslateTextField(
                        sourceText = uiState.sourceText,
                        targetText = uiState.targetText,
                        isLoading = uiState.isTargetTextLoading,
                        onSourceTextChanged = onSourceTextChanged,
                        onTargetTextChanged = onTargetTextChanged,
                        onTranslateText = onTranslateText,
                    )
                    Row(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("翻訳切り替え", color = MaterialTheme.colorScheme.onPrimaryContainer)
                            Switch(
                                checked = uiState.isSwitchChecked,
                                onCheckedChange = {
                                    onTargetLanguageChanged()
                                    onToggleSwitch(it)
                                }
                            )
                            when(uiState.targetLanguage){
                                Languages.ENGLISH -> {
                                    Text(
                                        text = "和訳",
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                                Languages.JAPANESE -> {
                                    Text(
                                        text = "英訳",
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }
                        }
                        Button(
                            onClick = { onAddButtonClicked() },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                            )
                        ){
                            Text("追加")
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.padding(32.dp))
        }
    }
}

@Composable
fun TranslateTextField(
    modifier: Modifier = Modifier,
    sourceText: String,
    targetText: String,
    isLoading: Boolean,
    onSourceTextChanged: (String) -> Unit,
    onTargetTextChanged: (String) -> Unit,
    onTranslateText: () -> Unit,
) {
    val localFocusManager = LocalFocusManager.current
    Column(modifier = modifier) {
        TextField(
            value = sourceText,
            onValueChange = {
                onTranslateText()
                onSourceTextChanged(it)
            },
            textStyle = MaterialTheme.typography.bodyMedium,
            placeholder = {
                androidx.compose.material3.Text(
                    text = "Input Text",
                    modifier = Modifier.alpha(0.3f),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { localFocusManager.clearFocus() })
        )
        Spacer(modifier = Modifier.padding(4.dp))
        TextField(
            value = targetText,
            onValueChange = { onTargetTextChanged(it) },
            textStyle = MaterialTheme.typography.bodyMedium,
            placeholder = {
                androidx.compose.material3.Text(
                    text = "After Translate",
                    modifier = Modifier.alpha(0.3f),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            },
            singleLine = true,
            trailingIcon = {
                if(isLoading) CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { localFocusManager.clearFocus() })
        )
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
            onToggleSwitch = {}
        )
    }
}