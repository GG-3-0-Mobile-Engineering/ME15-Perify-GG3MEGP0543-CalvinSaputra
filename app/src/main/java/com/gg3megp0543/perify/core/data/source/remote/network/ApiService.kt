package com.gg3megp0543.perify.core.data.source.remote.network

import com.gg3megp0543.perify.core.data.source.remote.response.DisasterReportResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("reports")
    suspend fun getDisasterReport(
        @Query("timeperiod") timeperiod: Int? = 172800,
        @Query("admin") admin: String? = null,
        @Query("disaster") disaster: String? = null
    ): Response<DisasterReportResponse>
}