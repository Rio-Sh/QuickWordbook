/* (C)2022 Rio-Sh */
package com.io.github.rio_sh.quickwordbook.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class QuickWordbookDatabase : RoomDatabase() {
    abstract fun wordsDao(): WordsDao
}
