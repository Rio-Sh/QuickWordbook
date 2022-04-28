/* (C)2022 Rio-Sh */
package com.io.github.rio_sh.quickwordbook.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.io.github.rio_sh.quickwordbook.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class WordsDaoTest {
    private lateinit var database: QuickWordbookDatabase
    private lateinit var dao: WordsDao

    private val wordItem1 = Word(textSource = "Text1", textTarget = "テキスト1", lastEdit = 0L)
    private val wordItem2 = Word(textSource = "Text2", textTarget = "テキスト2", lastEdit = 0L)

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            QuickWordbookDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.wordsDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertThenGetWord() = runTest {
        dao.insertWord(wordItem1)
        val result = dao.getWordById(1)

        val expect = Word(wordId = 1, textSource = "Text1", textTarget = "テキスト1", lastEdit = 0L)
        assertThat(result).isEqualTo(expect)
    }

    @Test
    fun updateWordAndGetById() = runTest {
        dao.insertWord(wordItem1)
        dao.updateWord(
            Word(
                wordId = 1,
                textSource = "New text",
                textTarget = "新しいテキスト",
                lastEdit = 1L
            )
        )
        val result = dao.getWordById(1)

        val expect = Word(
            wordId = 1,
            textSource = "New text",
            textTarget = "新しいテキスト",
            lastEdit = 1L
        )
        assertThat(result).isEqualTo(expect)
    }

    @Test
    fun observeWords() = runTest {
        dao.insertWord(wordItem1)
        dao.insertWord(wordItem2)

        // val result = dao.observeAllWords().getOrAwaitValue()
        val result = dao.observeAllWords().first()

        val expect1 = Word(wordId = 1, textSource = "Text1", textTarget = "テキスト1", lastEdit = 0L)
        val expect2 = Word(wordId = 2, textSource = "Text2", textTarget = "テキスト2", lastEdit = 0L)

        assertThat(result).contains(expect1, expect2)
    }

    @Test
    fun observeLastFive() = runTest {
        for (i in 1..6) {
            dao.insertWord(
                Word(
                    textSource = "Text$i",
                    textTarget = "テキスト$i",
                    lastEdit = i.toLong()
                )
            )
        }

        val result = dao.observeLastEditFive().first()

        for (i in 2..6) {
            assertThat(result).contains(
                Word(
                    wordId = i,
                    textSource = "Text$i",
                    textTarget = "テキスト$i",
                    lastEdit = i.toLong()
                )
            )
        }
        assertThat(result).doesNotContain(
            Word(
                wordId = 1,
                textSource = "Text1",
                textTarget = "テキスト1",
                lastEdit = 1L
            )
        )
    }

    @Test
    fun deleteWordAndObserveAll() = runTest {
        dao.insertWord(wordItem1)
        dao.insertWord(wordItem2)

        dao.deleteWordById(1)

        val result = dao.observeAllWords().first()

        val expect1 = Word(wordId = 1, textSource = "Text1", textTarget = "テキスト1", lastEdit = 0L)
        val expect2 = Word(wordId = 2, textSource = "Text2", textTarget = "テキスト2", lastEdit = 0L)

        assertThat(result).contains(expect2)
        assertThat(result).doesNotContain(expect1)
    }

    @Test
    fun deleteAllWordsAndObserveAll() = runTest {
        for (i in 1..6) {
            dao.insertWord(
                Word(
                    textSource = "Text$i",
                    textTarget = "テキスト$i",
                    lastEdit = i.toLong()
                )
            )
        }

        dao.deleteAllWords()

        val result = dao.observeAllWords().first()

        assertThat(result).isEmpty()
    }
}
