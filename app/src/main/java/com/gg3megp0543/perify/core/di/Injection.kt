package com.gg3megp0543.perify.core.di

import com.gg3megp0543.perify.core.data.source.remote.network.ApiConfig
import com.gg3megp0543.perify.core.data.DisasterRepository

object Injection {
    fun provideDisasterRepository(): DisasterRepository {
        val apiService = ApiConfig.getApiService()
        return DisasterRepository.getInstance(apiService)
    }
}