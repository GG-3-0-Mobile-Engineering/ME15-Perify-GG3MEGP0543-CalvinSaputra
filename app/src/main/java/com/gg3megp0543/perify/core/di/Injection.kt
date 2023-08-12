package com.gg3megp0543.perify.core.di

import android.content.Context
import com.gg3megp0543.perify.core.data.DisasterRepository
import com.gg3megp0543.perify.core.data.source.local.LocalDataSource
import com.gg3megp0543.perify.core.data.source.local.room.DisasterDatabase
import com.gg3megp0543.perify.core.data.source.remote.RemoteDataSource
import com.gg3megp0543.perify.core.data.source.remote.network.ApiConfig
import com.gg3megp0543.perify.core.domain.repository.IDisasterRepository
import com.gg3megp0543.perify.core.domain.usecase.DisasterImpl
import com.gg3megp0543.perify.core.domain.usecase.DisasterUseCase

object Injection {
    fun provideRepository(context: Context): IDisasterRepository {
        val database = DisasterDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.getApiService())
        val localDataSource = LocalDataSource.getInstance(database.disasterDao())

        return DisasterRepository.getInstance(remoteDataSource, localDataSource)
    }

    fun provideDisasterUseCase(context: Context): DisasterUseCase {
        val repository = provideRepository(context)
        return DisasterImpl(repository)
    }
}