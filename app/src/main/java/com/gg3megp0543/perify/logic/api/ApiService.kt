package com.gg3megp0543.perify.logic.api

import com.gg3megp0543.perify.logic.response.DisasterReportResponse
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