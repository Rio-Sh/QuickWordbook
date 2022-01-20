package com.io.github.rio_sh.quickwordbook.di

import android.content.Context
import androidx.room.Room
import com.io.github.rio_sh.quickwordbook.data.QuickWordBookDatabase
import com.io.github.rio_sh.quickwordbook.data.WordsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideWordsDao(quickWordBookDatabase: QuickWordBookDatabase): WordsDao {
        return quickWordBookDatabase.wordsDao()
    }

    @Provides
    @Singleton
    fun provideQuickWordBookDatabase(@ApplicationContext appContext : Context): QuickWordBookDatabase {
        return Room.databaseBuilder(
            appContext,
            QuickWordBookDatabase::class.java,
            "QuickWordBookDatabase"
        ).build()
    }
}
