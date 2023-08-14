package com.gg3megp0543.perify.di

import com.gg3megp0543.perify.core.domain.usecase.DisasterImpl
import com.gg3megp0543.perify.core.domain.usecase.DisasterUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {
    @Binds
    @ViewModelScoped
    abstract fun provideDisasterUseCase(disasterImpl: DisasterImpl): DisasterUseCase
}