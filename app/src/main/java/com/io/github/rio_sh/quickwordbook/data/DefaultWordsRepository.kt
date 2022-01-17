package com.io.github.rio_sh.quickwordbook.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

// LocalDatasource's methods is assigned IO dispatcher at definition.
// Can refactor this as use another datasource. Add a datasource to constructor, and invoke methods in inside coroutineScope block.
class DefaultWordsRepository(
    private val wordsLocalDataSource: WordsLocalDataSource,
    // private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun insertWord(word: Word) {
        coroutineScope {
            launch { wordsLocalDataSource.insetWord(word) }
        }
    }

    suspend fun updateWord(word: Word) {
        coroutineScope {
            launch { wordsLocalDataSource.updateWord(word) }
        }
    }


    suspend fun deleteWordById(wordId: Int) {
        coroutineScope {
            launch { wordsLocalDataSource.deleteWordById(wordId) }
        }
    }


    suspend fun deleteAllWord() {
        coroutineScope {
            launch { wordsLocalDataSource.deleteAllWord() }
        }
    }

    suspend fun getWordById(wordId: Int) {
        coroutineScope {
            launch { wordsLocalDataSource.getWordById(wordId) }
        }
    }

    fun observeAllWords(): LiveData<List<Word>> {
        return  wordsLocalDataSource.observeAllWords()
    }

    fun observeLastEditFive(): LiveData<List<Word>> {
        return wordsLocalDataSource.observeLastEditFive()
    }

}