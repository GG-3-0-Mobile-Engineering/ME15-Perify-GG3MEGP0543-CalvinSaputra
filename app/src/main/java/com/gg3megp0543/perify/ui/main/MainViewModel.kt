package com.gg3megp0543.perify.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.gg3megp0543.perify.logic.common.ApiRes
import com.gg3megp0543.perify.logic.data.DisasterRepository
import com.gg3megp0543.perify.logic.model.Properties
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val disasterRepository: DisasterRepository) : ViewModel() {
    private val _properties = MutableLiveData<List<Properties>>()
    val properties: LiveData<List<Properties>> = _properties

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    init {
        showDisasterReport(timeperiod = 86400)
        Log.d("MVM", "showDisasterReport is called")
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
                    is ApiRes.Success -> _properties.value = result.data
                    is ApiRes.Error -> _error.value = result.throwable
                }
            } catch (t: Throwable) {
                _error.value = t
            }
        }
    }
}

class MainViewModelFactory(private val disasterRepository: DisasterRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(disasterRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
