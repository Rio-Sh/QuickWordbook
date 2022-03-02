package com.io.github.rio_sh.quickwordbook.ui.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.io.github.rio_sh.quickwordbook.R
import com.io.github.rio_sh.quickwordbook.data.Word
import com.io.github.rio_sh.quickwordbook.ui.theme.QuickWordbookTheme
import com.io.github.rio_sh.quickwordbook.ui.theme.Shapes

/**
 * Display a Card of Word
 * Long texts is omitted by ellipsis
 * @param backgroundColor default color is primaryContainer
 * @param contentColor default color is onPrimaryContainer
 * @param onDeleteWord (event) request delete a word
 */
@Composable
fun WordCard(
    modifier: Modifier = Modifier,
    word: Word,
    backgroundColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    navigationToDetail: (wordId: Int) -> Unit,
    expanded: Boolean = false,
    onDeleteWord: (wordId: Int) -> Unit
) {
    var isExpandedCard by remember { mutableStateOf(expanded) }
    var isOpenDialog by remember { mutableStateOf(false) }
    var isOpenDropdown by remember { mutableStateOf(false) }

    Card(
        modifier = modifier,
        shape = Shapes.medium,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = word.textSource,
                    modifier = Modifier.padding(start = 16.dp),
                    color = contentColor,
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
                Box {
                    IconButton(onClick = { isOpenDropdown = true }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = null
                        )
                    }
                    if (isOpenDropdown) {
                        DropdownMenu(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.surface)
                                .clip(Shapes.small),
                            expanded = isOpenDropdown,
                            onDismissRequest = { isOpenDropdown = false }
                        ) {
                            DropdownMenuItem(onClick = {
                                isOpenDropdown = false
                                navigationToDetail(word.wordId)
                            }) {
                                Text(
                                    text = stringResource(R.string.edit),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            DropdownMenuItem(onClick = {
                                isOpenDropdown = false
                                isOpenDialog = true
                            }) {
                                Text(
                                    text = stringResource(R.string.delete),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
            AnimatedVisibility(
                visible = isExpandedCard,
                enter = expandVertically(expandFrom = Alignment.Top),
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        word.textTarget,
                        modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                        color = contentColor,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                }
            }
            if (isExpandedCard) {
                Icon(
                    modifier = Modifier.clickable { isExpandedCard = !isExpandedCard },
                    imageVector = Icons.Filled.ExpandLess,
                    contentDescription = null
                )
            } else {
                Icon(
                    modifier = Modifier.clickable { isExpandedCard = !isExpandedCard },
                    imageVector = Icons.Filled.ExpandMore,
                    contentDescription = null
                )
            }
        }
        if (isOpenDialog) {
            AlertDialog(
                onDeleteWord = { onDeleteWord(word.wordId) },
                openDialog = { isOpenDialog = it },
            )
        }
    }
}

@Composable
private fun AlertDialog(
    onDeleteWord: () -> Unit,
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
        title = { Text(text = stringResource(R.string.do_you_remove_this_word)) },
        text = { Text(text = stringResource(R.string.cant_cancel_this_operation)) },
        confirmButton = {
            TextButton(
                onClick = {
                    onDeleteWord()
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


@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun WordCardPreview() {
    val sampleWord = Word(0, "Text Text", "テキスト テキスト", 0L)
    QuickWordbookTheme {
        Surface {
            WordCard(
                modifier = Modifier.width(240.dp),
                word = sampleWord,
                navigationToDetail = {},
                onDeleteWord = {}
            )
        }
    }
}