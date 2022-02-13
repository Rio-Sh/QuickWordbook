package com.io.github.rio_sh.quickwordbook.di

import android.content.Context
import androidx.room.Room
import com.io.github.rio_sh.quickwordbook.data.QuickWordbookDatabase
import com.io.github.rio_sh.quickwordbook.data.WordsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideWordsDao(quickWordbookDatabase: QuickWordbookDatabase): WordsDao {
        return quickWordbookDatabase.wordsDao()
    }

    @Provides
    @Singleton
    fun provideQuickWordBookDatabase(@ApplicationContext appContext : Context): QuickWordbookDatabase {
        return Room.databaseBuilder(
            appContext,
            QuickWordbookDatabase::class.java,
            "QuickWordBookDatabase"
        ).build()
    }

    @IoDispatcher
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher
