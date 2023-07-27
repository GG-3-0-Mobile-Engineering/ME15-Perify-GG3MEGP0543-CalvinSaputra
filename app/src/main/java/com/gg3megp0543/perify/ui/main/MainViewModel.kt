package com.gg3megp0543.perify.ui.main

import androidx.lifecycle.*
import com.gg3megp0543.perify.logic.common.ApiRes
import com.gg3megp0543.perify.logic.data.DisasterRepository
import com.gg3megp0543.perify.logic.di.Injection
import com.gg3megp0543.perify.logic.response.Properties
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
    val propertiesWithCoordinates: LiveData<List<Pair<Properties, List<Any?>>>> =
        _propertiesWithCoordinates

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    init {
        showDisasterReport()
    }

    fun showDisasterReport(
        timeperiod: Int? = 604800,
        admin: String? = null,
        disaster: String? = null
    ) {
        if (_loadingState.value != true) {
            viewModelScope.launch {
                try {
                    _loadingState.value = true

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
                } finally {
                    _loadingState.value = false
                }
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

