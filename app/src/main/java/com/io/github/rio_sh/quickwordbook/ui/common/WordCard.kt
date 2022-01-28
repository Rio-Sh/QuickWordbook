package com.io.github.rio_sh.quickwordbook.ui.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.io.github.rio_sh.quickwordbook.data.Word
import com.io.github.rio_sh.quickwordbook.ui.theme.QuickWordbookTheme
import com.io.github.rio_sh.quickwordbook.ui.theme.Shapes

@Composable
fun WordCard(
    modifier: Modifier = Modifier,
    word: Word,
    navigationToDetail: (wordId: Int) -> Unit,
    deleteWord: (wordId: Int) -> Unit
) {
    var expandedCard by remember { mutableStateOf(false) }
    var openDialog by remember { mutableStateOf(false) }
    var openDropdown by remember { mutableStateOf(false) }

    Card(
        modifier = modifier,
        shape = Shapes.medium,
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Text(
                    text = word.textSource,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
                Box(contentAlignment = Alignment.TopEnd) {
                    IconButton(onClick = { openDropdown = true }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = null
                        )
                    }
                    if (openDropdown) {
                        DropdownMenu(
                            modifier = Modifier.background(MaterialTheme.colorScheme.surface).clip(Shapes.small),
                            expanded = openDropdown,
                            onDismissRequest = { openDropdown = false }
                        ) {
                            DropdownMenuItem(onClick = { navigationToDetail(word.wordId) }) {
                                Text("編集", color = MaterialTheme.colorScheme.onSurface)
                            }
                            DropdownMenuItem(onClick = { openDialog = true}) {
                                Text("削除", color = MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }
                }
            }
            // TODO Add Animation
            if (expandedCard) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        word.textTarget,
                        modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                    IconButton(onClick = { expandedCard = false }) {
                        Icon(imageVector = Icons.Filled.ExpandLess, contentDescription = null)
                    }
                }
            } else {
                IconButton(onClick = { expandedCard = true }) {
                    Icon(imageVector = Icons.Filled.ExpandMore, contentDescription = null)
                }
            }

        }
        if (openDialog) {
            AlertDialog(
                containerColor = MaterialTheme.colorScheme.surface,
                onDismissRequest = { openDialog = false },
                icon = {
                    Icon(
                        imageVector = Icons.Filled.DeleteForever,
                        contentDescription = null
                    )
                },
                title = { Text( text = "この単語を削除しますか？" ) },
                text = { Text(text = "この操作は取り消せません") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            deleteWord(word.wordId)
                            openDialog  = false
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
                        onClick = { openDialog = false },
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onSurface)
                    ) {
                        Text(text = "キャンセル")
                    }
                }
            )
        }
    }
}



@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun WordCardPreview() {
    val sampleWord = Word(0, "Text Text", "テキスト テキスト", 0L)
    QuickWordbookTheme {
        WordCard(word = sampleWord, navigationToDetail = {}, deleteWord = {})
    }
}