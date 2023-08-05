package com.gg3megp0543.perify.core.data

import com.gg3megp0543.perify.core.data.source.remote.network.ApiService
import com.gg3megp0543.perify.core.data.source.remote.response.Properties
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DisasterRepository(private val apiService: ApiService) {
    suspend fun getDisasterReport(
        timeperiod: Int? = null,
        admin: String? = null,
        disaster: String? = null
    ): ApiRes<List<Pair<Properties, List<Any?>>>> = withContext(Dispatchers.IO) {
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
                    ApiRes.Success(data)
                } else {
                    ApiRes.Error(Throwable("Response body is null"))
                }
            } else {
                ApiRes.Error(Throwable("API call failed with code ${response.code()}"))
            }
        } catch (t: Throwable) {
            ApiRes.Error(t)
        }
    }

    companion object {
        @Volatile
        private var instance: DisasterRepository? = null
        fun getInstance(
            apiService: ApiService,
        ): DisasterRepository =
            instance ?: synchronized(this) {
                instance ?: DisasterRepository(apiService)
            }.also { instance = it }
    }
}
