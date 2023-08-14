package com.gg3megp0543.perify.core.di

import com.gg3megp0543.perify.core.data.DisasterRepository
import com.gg3megp0543.perify.core.domain.repository.IDisasterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(disasterRepository: DisasterRepository): IDisasterRepository
}