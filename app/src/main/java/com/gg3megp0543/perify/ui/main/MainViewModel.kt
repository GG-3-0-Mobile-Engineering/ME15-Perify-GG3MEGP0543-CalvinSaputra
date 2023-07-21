package com.gg3megp0543.perify.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gg3megp0543.perify.logic.api.ApiConfig
import com.gg3megp0543.perify.logic.model.DisasterReportResponse
import com.gg3megp0543.perify.logic.model.Properties
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class MainViewModel : ViewModel() {
    private val _properties = MutableLiveData<List<Properties>>()
    val properties: LiveData<List<Properties>> = _properties

    init {
        showDisasterReport()
    }

    private fun showDisasterReport(
        timeperiod: Int? = null,
        admin: String? = null,
        disaster: String? = null
    ) {
        val client = ApiConfig.getApiService().getDisasterReport(timeperiod, admin, disaster)
        client.enqueue(object : Callback<DisasterReportResponse> {
            override fun onResponse(
                call: Call<DisasterReportResponse>,
                response: Response<DisasterReportResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    val propertiesList = data?.result?.objects?.output?.geometries?.mapNotNull {
                        it?.properties
                    }
                    if (propertiesList != null) {
                        _properties.value = propertiesList
                    }

                }
            }

            override fun onFailure(call: Call<DisasterReportResponse>, t: Throwable) {
                when (t) {
                    is SocketTimeoutException, is UnknownHostException -> {
                        Log.d("MainViewModel", "No Connection,")
                    }

                    is HttpException -> {
                        Log.d("MainViewModel", "Server Error:" + t.code())
                    }

                    else -> {
                        Log.d("MainViewModel", "Generic Rrror:")
                    }
                }
            }
        })
    }
}