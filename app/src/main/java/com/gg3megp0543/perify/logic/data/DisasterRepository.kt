package com.gg3megp0543.perify.logic.data

import com.gg3megp0543.perify.logic.api.ApiService
import com.gg3megp0543.perify.logic.common.ApiRes
import com.gg3megp0543.perify.logic.model.Properties
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DisasterRepository(private val apiService: ApiService) {
    suspend fun getDisasterReport(
        timeperiod: Int? = null,
        admin: String? = null,
        disaster: String? = null
    ): ApiRes<List<Properties>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getDisasterReport(timeperiod, admin, disaster)

            if (response.isSuccessful) {
                val responseData = response.body()
                if (responseData != null) {
                    val data = responseData.result?.objects?.output?.geometries?.mapNotNull {
                        it?.properties
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
