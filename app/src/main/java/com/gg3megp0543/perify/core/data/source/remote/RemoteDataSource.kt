package com.gg3megp0543.perify.core.data.source.remote

import android.util.Log
import com.gg3megp0543.perify.core.data.source.remote.network.ApiResponse
import com.gg3megp0543.perify.core.data.source.remote.network.ApiService
import com.gg3megp0543.perify.core.data.source.remote.response.GeometriesItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getAllDisaster(
        admin: String?,
        disaster: String?
    ): Flow<ApiResponse<List<GeometriesItem?>>> {
        return flow {
            try {
                val response = apiService.getDisasterReport(admin = admin, disaster = disaster)
                val dataArray = response.result?.objects?.output?.geometries
                if (dataArray != null) {
                    if (dataArray.isNotEmpty()) {
                        emit(ApiResponse.Success(response.result?.objects?.output?.geometries))
                    } else {
                        emit(ApiResponse.Empty)
                    }
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}