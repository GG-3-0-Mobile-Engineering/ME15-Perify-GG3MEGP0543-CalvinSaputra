package com.gg3megp0543.perify.core.di

import android.content.Context
import androidx.room.Room
import com.gg3megp0543.perify.core.data.source.local.room.DisasterDao
import com.gg3megp0543.perify.core.data.source.local.room.DisasterDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides

    fun provideDatabase(@ApplicationContext context: Context): DisasterDatabase =
        Room.databaseBuilder(
            context,
            DisasterDatabase::class.java, "Disaster.db"
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideDisasterDao(database: DisasterDatabase): DisasterDao = database.disasterDao()
}