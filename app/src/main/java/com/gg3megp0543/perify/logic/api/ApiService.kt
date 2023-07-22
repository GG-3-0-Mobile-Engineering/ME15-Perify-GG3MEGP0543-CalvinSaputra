package com.gg3megp0543.perify.logic.api

import com.gg3megp0543.perify.logic.model.DisasterReportResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("reports")
    suspend fun getDisasterReport(
        @Query("timeperiod") timeperiod: Int? = null,
        @Query("admin") admin: String? = null,
        @Query("disaster") disaster: String? = null
    ): Response<DisasterReportResponse>
}