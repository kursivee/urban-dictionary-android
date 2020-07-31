package com.kursivee.urbandictionary.common.di

import android.content.Context
import androidx.room.Room
import com.kursivee.urbandictionary.common.db.AppDatabase
import com.kursivee.urbandictionary.results.data.source.CachedResultDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ApplicationDatabaseModule {
    @Provides
    @Singleton
    fun provideRoomDB(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java, "result"
        ).build()
    }

    @Provides
    @Singleton
    fun provideResultDao(database: AppDatabase): CachedResultDao {
        return database.resultDao()
    }
}
