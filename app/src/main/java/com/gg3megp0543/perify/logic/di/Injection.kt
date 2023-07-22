package com.gg3megp0543.perify.logic.di

import com.gg3megp0543.perify.logic.api.ApiConfig
import com.gg3megp0543.perify.logic.data.DisasterRepository

object Injection {
    fun provideDisasterRepository(): DisasterRepository{
        val apiService = ApiConfig.getApiService()
        return DisasterRepository.getInstance(apiService)
    }
}