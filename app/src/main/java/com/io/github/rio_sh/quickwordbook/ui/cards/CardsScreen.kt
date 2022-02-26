package com.io.github.rio_sh.quickwordbook.ui.cards

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.ModeEdit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.io.github.rio_sh.quickwordbook.R
import com.io.github.rio_sh.quickwordbook.data.Word
import com.io.github.rio_sh.quickwordbook.ui.common.WordCard
import com.io.github.rio_sh.quickwordbook.ui.theme.QuickWordbookTheme
import kotlin.math.max

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalMaterial3Api
@Composable
fun CardsBody(
    uiState: CardsUiState,
    onBackClicked: () -> Unit,
    onAddFabClicked: () -> Unit,
    onEditClicked: (wordId: Int) -> Unit,
    onDeleteWord: (wordId: Int) -> Unit,
    toggleCardsExpand: (Boolean) -> Unit,
    onDeleteAllWords: () -> Unit
) {
    var isOpenDialog by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            AppBar(
                onBackClicked = onBackClicked,
                isCardsOpen = uiState.isCardsOpen,
                toggleCardsExpand = toggleCardsExpand,
                openDialog = { isOpenDialog = it }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAddFabClicked() },
                modifier = Modifier.navigationBarsPadding()
            ) {
                Icon(
                    imageVector = Icons.Rounded.ModeEdit,
                    contentDescription = ""
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.padding(36.dp))
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.your_wordbook),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            if(uiState.isWordsLoadingFailed){
                Text(
                    text = stringResource(R.string.error_cant_load_words),
                    modifier = Modifier.fillMaxWidth(1f),
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }
            StaggerLayout(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
            ) {
                uiState.words.forEach { word ->
                    if (!uiState.isCardsOpen) {
                        WordCard(
                            modifier = Modifier.padding(
                                start = 8.dp,
                                top = 16.dp,
                                end = 8.dp,
                                bottom = 16.dp
                            ),
                            word = word,
                            navigationToDetail = onEditClicked,
                            expanded = false,
                            onDeleteWord = onDeleteWord
                        )
                    } else {
                        WordCard(
                            modifier = Modifier.padding(
                                start = 8.dp,
                                top = 16.dp,
                                end = 8.dp,
                                bottom = 16.dp
                            ),
                            word = word,
                            navigationToDetail = onEditClicked,
                            expanded = true,
                            onDeleteWord = onDeleteWord
                        )
                    }
                }
            }
        }
        if (isOpenDialog) {
            AlertDialog(
                onDeleteAllWords = onDeleteAllWords,
                openDialog = { isOpenDialog = it }
            )
        }
    }
}

@Composable
private fun AlertDialog(
    onDeleteAllWords: () -> Unit,
    openDialog: (Boolean) -> Unit,
) {
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.surface,
        onDismissRequest = { openDialog(false) },
        icon = {
            Icon(
                imageVector = Icons.Filled.DeleteForever,
                contentDescription = null
            )
        },
        title = { Text(text = stringResource(R.string.do_you_delete_all_words)) },
        text = { Text(text = stringResource(R.string.cant_cancel_this_operation)) },
        confirmButton = {
            TextButton(
                onClick = {
                    onDeleteAllWords()
                    openDialog(false)
                },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Text(stringResource(R.string.delete))
            }
        },
        dismissButton = {
            TextButton(
                onClick = { openDialog(false) },
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onSurface)
            ) {
                Text(text = stringResource(R.string.cancel))
            }
        }
    )
}

@Composable
private fun StaggerLayout(
    modifier: Modifier = Modifier,
    rows: Int = 4,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { mesurables, constraints ->
        val rowWidths = IntArray(rows) { 0 }
        val rowHeights = IntArray(rows) { 0 }

        val placeables = mesurables.mapIndexed { index, measurable ->
            val placeable = measurable.measure(constraints)

            // Track width and max height of each row
            val row = index % rows
            rowWidths[row] += placeable.width
            rowHeights[row] = max(rowHeights[row], placeable.height)

            placeable
        }

        // Grid's of rows
        val width = rowWidths.maxOrNull()?.coerceIn(constraints.minWidth, constraints.maxWidth)
            ?: constraints.minWidth
        val height = rowHeights.sum().coerceIn(constraints.minHeight, constraints.maxHeight)

        // y co-ord of rows
        val rowY = IntArray(rows) { 0 }
        for (i in 1 until rows) {
            rowY[i] = rowY[i - 1] + rowHeights[i - 1]
        }
        layout(width, height) {
            val rowX = IntArray(rows) { 0 }
            placeables.forEachIndexed { index, placeable ->
                val row = index % rows
                placeable.place(
                    x = rowX[row],
                    y = rowY[row]
                )
                rowX[row] += placeable.width
            }
        }
    }
}

@Composable
private fun AppBar(
    onBackClicked: () -> Unit,
    isCardsOpen: Boolean,
    toggleCardsExpand: (Boolean) -> Unit,
    openDialog: (Boolean) -> Unit
) {
    var isOpenDropdown by remember { mutableStateOf(false) }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(onClick = onBackClicked) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null
            )
        }
        Box {
            IconButton(onClick = { isOpenDropdown = true }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = null
                )
            }
            if (isOpenDropdown) {
                DropdownMenu(
                    modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                    expanded = isOpenDropdown,
                    onDismissRequest = { isOpenDropdown = false }
                ) {
                    if (!isCardsOpen) {
                        DropdownMenuItem(onClick = { toggleCardsExpand(true) }) {
                            Text(stringResource(R.string.open_all))
                        }
                    } else {
                        DropdownMenuItem(onClick = { toggleCardsExpand(false) }) {
                            Text(stringResource(R.string.close_all))
                        }
                    }
                    DropdownMenuItem(
                        onClick = { openDialog(true) },
                        modifier = Modifier.background(MaterialTheme.colorScheme.error),
                    ) {
                        Text(
                            stringResource(R.string.delete_all),
                            color = MaterialTheme.colorScheme.onError
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun CardsScreenPreview() {
    val wordsList: List<Word> = List(20) {
        Word(it, "Text $it".repeat((1..5).random()), "テキスト $it", 0)
    }
    // val wordsList = emptyList<Word>()
    val uiState = CardsUiState(
        isLoading = false,
        isCardsOpen = true,
        words = wordsList,
        // isWordsLoadingFailed = true
    )
    QuickWordbookTheme {
        CardsBody(
            uiState = uiState,
            onBackClicked = {},
            onAddFabClicked = {},
            onEditClicked = {},
            onDeleteWord = {},
            toggleCardsExpand = {},
            onDeleteAllWords = {}
        )
    }
}