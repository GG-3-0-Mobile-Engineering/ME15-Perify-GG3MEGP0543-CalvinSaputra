package com.gg3megp0543.perify.core.presenter.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gg3megp0543.perify.core.domain.usecase.DisasterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val disasterUseCase: DisasterUseCase) :
    ViewModel() {

    fun getAllDisaster(location: String?, disaster: String?) =
        disasterUseCase.getAllDisaster(admin = location, disaster = disaster).asLiveData()
}

