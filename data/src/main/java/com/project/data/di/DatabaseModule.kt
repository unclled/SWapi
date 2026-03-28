package com.project.data.di

import android.content.Context
import androidx.room.Room
import com.project.data.local.SwapiDatabase
import com.project.data.local.dao.CharacterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideSwapiDatabase(@ApplicationContext context: Context): SwapiDatabase {
        return Room.databaseBuilder(
            context,
            SwapiDatabase::class.java,
            "swapi_database.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCharacterDao(database: SwapiDatabase): CharacterDao {
        return database.characterDao
    }
}