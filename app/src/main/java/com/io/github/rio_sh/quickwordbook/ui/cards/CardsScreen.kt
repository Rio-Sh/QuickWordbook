package com.io.github.rio_sh.quickwordbook.ui.cards

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
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
    expandOrCloseCards: () -> Unit,
    onDeleteAllWords: () -> Unit
) {
    var isOpenDialog by remember { mutableStateOf(false) }
    Scaffold(
        topBar = { AppBar(
            onBackClicked = onBackClicked,
            expandOrCloseCards = expandOrCloseCards,
            openDialog = { isOpenDialog = it }
        ) },
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
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.padding(56.dp))
            Text(
                text = "Your Wordbook",
                style = MaterialTheme.typography.headlineSmall
            )
            StaggerLayout(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
            ) {
                uiState.words.forEach { word ->
                    WordCard(
                        modifier = Modifier.padding(
                            start = 8.dp,
                            top = 16.dp,
                            end = 8.dp,
                            bottom = 16.dp
                        ),
                        word = word,
                        navigationToDetail = onEditClicked,
                        onDeleteWord = onDeleteWord
                    )
                }
            }
        }
        if(isOpenDialog) {
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
        title = { Text(text = "全ての単語を削除しますか？") },
        text = { Text(text = "この操作は取り消せません") },
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
                Text("削除")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { openDialog(false) },
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onSurface)
            ) {
                Text(text = "キャンセル")
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
        val width = rowWidths.maxOrNull()?.coerceIn(constraints.minWidth, constraints.maxWidth) ?: constraints.minWidth
        val height = rowHeights.sum().coerceIn(constraints.minHeight, constraints.maxHeight)

        // y co-ord of rows
        val rowY = IntArray(rows) { 0 }
        for (i in 1 until rows) {
            rowY[i] = rowY[i - 1] + rowHeights[i -1]
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
    expandOrCloseCards: () -> Unit,
    openDialog: (Boolean) -> Unit
) {
    var isOpenDropdown by remember { mutableStateOf(false) }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
    ){
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
                    expanded = isOpenDropdown,
                    onDismissRequest = { isOpenDropdown = false}
                ) {
                    DropdownMenuItem(onClick = expandOrCloseCards) {
                        Text("Open All")
                    }
                    DropdownMenuItem(
                        onClick = { openDialog(true) },
                        modifier = Modifier.background(MaterialTheme.colorScheme.error),
                    ) {
                        Text("全て削除", color = MaterialTheme.colorScheme.onError)
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
    val uiState = CardsUiState(isLoading = false, isCardsOpen = false, words = wordsList)
    QuickWordbookTheme {
        CardsBody(
            uiState = uiState,
            onBackClicked = {},
            onAddFabClicked = {},
            onEditClicked = {},
            onDeleteWord = {},
            expandOrCloseCards = {},
            onDeleteAllWords = {}
        )
    }
}