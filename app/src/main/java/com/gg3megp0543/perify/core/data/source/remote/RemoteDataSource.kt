package com.gg3megp0543.perify.core.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gg3megp0543.perify.core.data.source.remote.network.ApiResponse
import com.gg3megp0543.perify.core.data.source.remote.network.ApiService
import com.gg3megp0543.perify.core.data.source.remote.response.DisasterReportResponse
import com.gg3megp0543.perify.core.data.source.remote.response.Properties
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteDataSource private constructor(private val apiService: ApiService) {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(service)
            }
    }

    fun getAllDisaster(): LiveData<ApiResponse<List<DisasterReportResponse>>> {
        val resultData = MutableLiveData<ApiResponse<List<DisasterReportResponse>>>()

        suspend fun getDisasterReport(
            timeperiod: Int? = null,
            admin: String? = null,
            disaster: String? = null
        ): ApiResponse<List<Pair<Properties, List<Any?>>>> = withContext(Dispatchers.IO) {
            try {
                val response = apiService.getDisasterReport(timeperiod, admin, disaster)

                if (response.isSuccessful) {
                    val responseData = response.body()
                    if (responseData != null) {
                        val data =
                            responseData.result?.objects?.output?.geometries?.mapNotNull { geometry ->
                                geometry?.properties?.let { properties ->
                                    val coordinates = geometry.coordinates
                                    if (coordinates != null) {
                                        properties to coordinates
                                    } else {
                                        null
                                    }
                                }
                            } ?: emptyList()
                        ApiResponse.Success(data)
                    } else {
                        ApiResponse.Error("Response body is null")
                    }
                } else {
                    ApiResponse.Error("API call failed with code ${response.code()}")
                }
            } catch (t: Throwable) {
                ApiResponse.Error(t.toString())
            }
        }
        return resultData
    }
}