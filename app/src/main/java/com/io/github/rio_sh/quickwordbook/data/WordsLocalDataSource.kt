package com.io.github.rio_sh.quickwordbook.data

import com.io.github.rio_sh.quickwordbook.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WordsLocalDataSource @Inject constructor(
    private val wordsDao: WordsDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
){
    suspend fun insetWord(word: Word) = withContext(ioDispatcher) {
        wordsDao.insertWord(word)
    }

    suspend fun updateWord(word: Word) = withContext<Unit>(ioDispatcher) {
        wordsDao.updateWord(word)
    }

    suspend fun deleteWordById(wordId: Int) = withContext(ioDispatcher) {
        wordsDao.deleteWordById(wordId)
    }

    suspend fun deleteAllWord() = withContext(ioDispatcher) {
        wordsDao.deleteAllWords()
    }

    suspend fun getWordById(wordId: Int): Word = withContext(ioDispatcher) {
        wordsDao.getWordById(wordId)
    }

    fun observeAllWords(): Flow<List<Word>> {
        return wordsDao.observeAllWords()
    }

    fun observeLastEditFive(): Flow<List<Word>> {
       return wordsDao.observeLastEditFive()
    }

}