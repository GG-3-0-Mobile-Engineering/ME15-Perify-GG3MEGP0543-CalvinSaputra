package com.gg3megp0543.perify.core.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gg3megp0543.perify.core.di.Injection
import com.gg3megp0543.perify.core.domain.usecase.DisasterUseCase
import com.gg3megp0543.perify.core.presenter.main.MainViewModel

class ViewModelFactory private constructor(private val disasterUseCase: DisasterUseCase) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideDisasterUseCase(context))
            }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(disasterUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}