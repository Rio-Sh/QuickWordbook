package com.io.github.rio_sh.quickwordbook.ui.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ModeEdit
import androidx.compose.material.icons.rounded.ViewAgenda
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.io.github.rio_sh.quickwordbook.R
import com.io.github.rio_sh.quickwordbook.data.Word
import com.io.github.rio_sh.quickwordbook.ui.common.WordCard
import com.io.github.rio_sh.quickwordbook.ui.theme.QuickWordbookTheme

@ExperimentalMaterial3Api
@Composable
fun HomeBody(
    uiState: HomeUiState,
    onAddFabClicked: () -> Unit,
    onCardsFabClicked: () -> Unit,
    onEditClicked: (wordId: Int) -> Unit,
    onDeleteWord: (wordId: Int) -> Unit
) {
    Scaffold(
        // TODO replace title
        topBar = {
            Row(modifier = Modifier
                .padding(8.dp)
                .navigationBarsPadding()) {
                if(!isSystemInDarkTheme()){
                    Image(
                        painter = painterResource(id = R.drawable.ic_title),
                        contentDescription = null,
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.ic_title_dark),
                        contentDescription = null,
                    )
                }
            }
        },
        floatingActionButton = {
            Column(
                modifier = Modifier.navigationBarsPadding(),
                horizontalAlignment = Alignment.End
            ) {
                SmallFloatingActionButton(
                    modifier = Modifier.padding(bottom = 8.dp),
                    onClick = { onCardsFabClicked() }
                ){
                    Icon(
                        imageVector = Icons.Rounded.ViewAgenda,
                        contentDescription = ""
                    )
                }
                FloatingActionButton(onClick = { onAddFabClicked() }) {
                    Icon(
                        imageVector = Icons.Rounded.ModeEdit,
                        contentDescription = ""
                    )
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize(1f)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.padding(24.dp))
            if(uiState is HomeUiState.HasWords){
                Column(
                    modifier = Modifier.fillMaxWidth(1f),
                ) {
                    for ((index, word) in uiState.words.withIndex()){
                        HomeWordCard(
                            word = word,
                            index = index,
                            onEditClicked = onEditClicked,
                            onDeleteWord = onDeleteWord
                        )
                    }
                }
            } else {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = stringResource(R.string.add_words_to_your_wordbook),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun HomeWordCard(
    word: Word,
    index: Int,
    onEditClicked: (wordId: Int) -> Unit,
    onDeleteWord: (wordId: Int) -> Unit
) {
    Row(modifier = Modifier.padding(8.dp)){
        val stagger = if (index % 2 == 0) 72.dp else 16.dp
        Spacer(modifier = Modifier.width(stagger))
        WordCard(
            word = word,
            navigationToDetail =  { onEditClicked(word.wordId) },
            onDeleteWord = { onDeleteWord(word.wordId) }
        )
    }
}


@ExperimentalMaterial3Api
@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomePreview() {
    val wordsList: List<Word> = List(5) { Word(
        wordId = it,
        textSource = "Text $it",
        textTarget = "テキスト　$it",
        lastEdit = 0L
    ) }
    QuickWordbookTheme {
        HomeBody(
            uiState = HomeUiState.HasWords(
                isLoading = false,
                words = wordsList
            ),
            onAddFabClicked = {},
            onCardsFabClicked = {},
            onEditClicked = {},
            onDeleteWord = {}
        )
    }
}

@ExperimentalMaterial3Api
@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomeNoWordsPreview() {
    QuickWordbookTheme {
        HomeBody(
            uiState = HomeUiState.NoWords(
                isLoading = false
            ),
            onAddFabClicked = {},
            onCardsFabClicked = {},
            onEditClicked = {},
            onDeleteWord = {}
        )
    }
}
