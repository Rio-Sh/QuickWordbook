package com.io.github.rio_sh.quickwordbook.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WordsLocalDataSource (
    private val wordsDao: WordsDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
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

    suspend fun getWordById(wordId: Int) = withContext(ioDispatcher) {
        wordsDao.getWordById(wordId)
    }

    fun observeAllWords(): LiveData<List<Word>> {
        return wordsDao.observeAllWords()
    }

    fun observeLastEditFive(): LiveData<List<Word>> {
       return wordsDao.observeLastEditFive()
    }

}