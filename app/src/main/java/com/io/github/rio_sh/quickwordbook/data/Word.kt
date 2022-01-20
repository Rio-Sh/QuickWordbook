package com.io.github.rio_sh.quickwordbook.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @param wordId id of the word. It's generated automatically.
 * @param textSource text before translation.
 * @param textTarget text after translation.
 * @param lastEdit time of last Edit. type is Long and get from [System.currentTimeMillis]
 */
@Entity(tableName = "words")
data class Word (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "word_id")
    var wordId: Int = 0,

    @ColumnInfo(name = "text_source")
    var textSource: String = "",

    @ColumnInfo(name = "text_target")
    var textTarget: String = "",

    @ColumnInfo(name = "last_edit")
    var lastEdit: Long = System.currentTimeMillis()
 )