package com.rollncode.cleanarchitectureexample.di.modules

import android.content.Context
import androidx.room.Room
import com.rollncode.data.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "ExampleApp.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideDetailsDao(appDatabase: AppDatabase) = appDatabase.detailsDao
}