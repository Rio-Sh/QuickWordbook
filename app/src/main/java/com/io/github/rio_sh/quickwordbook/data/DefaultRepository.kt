package com.io.github.rio_sh.quickwordbook.data

import com.io.github.rio_sh.quickwordbook.network.GasService
import com.io.github.rio_sh.quickwordbook.network.Languages
import com.io.github.rio_sh.quickwordbook.network.ResponseText
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

// LocalDatasource's methods is assigned IO dispatcher at definition.
// Can refactor this as use another datasource. Add a datasource to constructor, and invoke methods in inside coroutineScope block.
class DefaultRepository @Inject constructor(
    private val wordsLocalDataSource: WordsLocalDataSource,
    private val gasService: GasService
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

    suspend fun getWordById(wordId: Int): Word {
        return coroutineScope {
            wordsLocalDataSource.getWordById(wordId)
        }
    }

    fun observeAllWords(): Flow<List<Word>> {
        return wordsLocalDataSource.observeAllWords()
    }

    fun observeLastEditFive(): Flow<List<Word>> {
        return wordsLocalDataSource.observeLastEditFive()
    }

    suspend fun translateText(
        sourceText: String,
        sourceLanguage: Languages,
        targetLanguages: Languages
    ): Response<ResponseText> {
        return gasService.getTranslateResponse(
            sourceText,
            sourceLanguage.languageCode,
            targetLanguages.languageCode
        )
    }
}