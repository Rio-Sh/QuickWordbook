package com.io.github.rio_sh.quickwordbook.ui.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.io.github.rio_sh.quickwordbook.R
import com.io.github.rio_sh.quickwordbook.network.Languages
import com.io.github.rio_sh.quickwordbook.ui.theme.QuickWordbookTheme
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
        color = MaterialTheme.colorScheme.secondaryContainer,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurfaceVariant)
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
                    Text(stringResource(R.string.switch_translation))
                    Switch(
                        checked = isSwitchChecked,
                        onCheckedChange = {
                            onTargetLanguageChanged()
                            onToggleSwitch(it)
                        }
                    )
                    when (targetLanguage) {
                        Languages.ENGLISH -> {
                            Text(text = stringResource(R.string.translate_to_english))
                        }
                        Languages.JAPANESE -> {
                            Text(text = stringResource(R.string.translate_to_japanese))
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
                onSourceTextChanged(it)
                onTranslateText()
            },
            textStyle = MaterialTheme.typography.bodyMedium,
            placeholder = {
                Text(
                    text = stringResource(R.string.input_text),
                    modifier = Modifier.alpha(0.3f),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { localFocusManager.clearFocus() }),
            colors = TextFieldDefaults.textFieldColors(textColor = MaterialTheme.colorScheme.onSecondaryContainer)
        )
        Spacer(modifier = Modifier.padding(4.dp))
        TextField(
            value = targetText,
            onValueChange = { onTargetTextChanged(it) },
            textStyle = MaterialTheme.typography.bodyMedium,
            placeholder = {
                Text(
                    text = stringResource(R.string.after_translation),
                    modifier = Modifier.alpha(0.3f),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            },
            singleLine = true,
            trailingIcon = {
                if (isLoading) CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { localFocusManager.clearFocus() }),
            colors = TextFieldDefaults.textFieldColors(textColor = MaterialTheme.colorScheme.onSecondaryContainer)
        )
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TextFieldPrev() {
     QuickWordbookTheme {
         Surface {
             TranslateTextField(
                 sourceText = "text",
                 targetText = "テキスト",
                 isLoading = false,
                 onSourceTextChanged = {},
                 onTargetTextChanged = {},
                 onTranslateText = {},
             )
         }
    }
}
