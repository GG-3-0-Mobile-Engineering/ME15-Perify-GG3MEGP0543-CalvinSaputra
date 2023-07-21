package com.gg3megp0543.perify.logic.api

import retrofit2.Call
import com.gg3megp0543.perify.logic.model.DisasterReportResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("reports")
    fun getDisasterReport(
        @Query("timeperiod") timeperiod: Int? = null,
        @Query("admin") admin: String? = null,
        @Query("disaster") disaster: String? = null
    ): Call<DisasterReportResponse>
}