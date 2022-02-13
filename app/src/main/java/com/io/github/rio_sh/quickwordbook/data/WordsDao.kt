package com.io.github.rio_sh.quickwordbook.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WordsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: Word)

    /**
     * @return The number of rows affected. It's always to be 1.
     */
    @Update
    suspend fun updateWord(word: Word): Int

    /**
     * @return The number of rows affected. It's always to be 1.
     */
    @Query("DELETE FROM words WHERE word_id = :wordId")
    suspend fun deleteWordById(wordId: Int) : Int

    @Query("DELETE FROM words")
    suspend fun deleteAllWords()

    @Query("SELECT * FROM words WHERE word_id = :wordId")
    suspend fun getWordById(wordId: Int): Word

    @Query("SELECT * FROM words")
    fun observeAllWords(): Flow<List<Word>>

    @Query("SELECT * FROM words ORDER BY last_edit DESC LIMIT 5")
    fun observeLastEditFive(): Flow<List<Word>>
}