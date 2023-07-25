package com.gg3megp0543.perify.ui.main

import androidx.lifecycle.*
import com.gg3megp0543.perify.logic.common.ApiRes
import com.gg3megp0543.perify.logic.data.DisasterRepository
import com.gg3megp0543.perify.logic.di.Injection
import com.gg3megp0543.perify.logic.model.Properties
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel private constructor(private val disasterRepository: DisasterRepository) :
    ViewModel() {
    companion object {
        @Volatile
        private var INSTANCE: MainViewModel? = null

        fun getInstance(disasterRepository: DisasterRepository): MainViewModel {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: MainViewModel(disasterRepository).also { INSTANCE = it }
            }
        }
    }

    private val _propertiesWithCoordinates = MutableLiveData<List<Pair<Properties, List<Any?>>>>()
    val propertiesWithCoordinates: LiveData<List<Pair<Properties, List<Any?>>>> = _propertiesWithCoordinates

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    init {
        showDisasterReport(timeperiod = 604800)
    }

    fun showDisasterReport(
        timeperiod: Int? = null,
        admin: String? = null,
        disaster: String? = null
    ) {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    disasterRepository.getDisasterReport(timeperiod, admin, disaster)
                }

                when (result) {
                    is ApiRes.Success -> {
                        _propertiesWithCoordinates.value = result.data
                    }
                    is ApiRes.Error -> _error.value = result.throwable
                }
            } catch (t: Throwable) {
                _error.value = t
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            val disasterRepository = Injection.provideDisasterRepository()
            return MainViewModel.getInstance(disasterRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
