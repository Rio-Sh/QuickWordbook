package com.io.github.rio_sh.quickwordbook.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Switch
import androidx.compose.material.TextField
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.io.github.rio_sh.quickwordbook.network.Languages
import com.io.github.rio_sh.quickwordbook.ui.theme.Shapes

@Composable
fun TranslateCard(
    sourceText: String,
    targetText: String,
    isTargetTextLoading: Boolean,
    targetLanguage: Languages,
    isSwitchChecked: Boolean,
    onSourceTextChanged: (String) -> Unit,
    onTargetTextChanged: (String) -> Unit,
    onTranslateText: () -> Unit,
    onTargetLanguageChanged: () -> Unit,
    onToggleSwitch: (Boolean) -> Unit,
    onDoneButtonClicked: () -> Unit,
    doneButtonText: @Composable () -> Unit,
) {
    Surface(
        shape = Shapes.medium,
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .width(IntrinsicSize.Max)
        ) {
            TranslateTextField(
                sourceText = sourceText,
                targetText = targetText,
                isLoading = isTargetTextLoading,
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
                    Text("翻訳切り替え")
                    Switch(
                        checked = isSwitchChecked,
                        onCheckedChange = {
                            onTargetLanguageChanged()
                            onToggleSwitch(it)
                        }
                    )
                    when (targetLanguage) {
                        Languages.ENGLISH -> {
                            Text(text = "和訳")
                        }
                        Languages.JAPANESE -> {
                            Text(text = "英訳")
                        }
                    }
                }
                Button(
                    onClick = { onDoneButtonClicked() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    )
                ) {
                    doneButtonText()
                }
            }
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
                Text(
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
                Text(
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