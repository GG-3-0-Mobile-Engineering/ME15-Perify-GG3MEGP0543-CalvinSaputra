package com.gg3megp0543.perify.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class PropertiesResponse(
    @field:SerializedName("image_url")
    val imageUrl: Any? = null,

    @field:SerializedName("disaster_type")
    val disasterType: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("source")
    val source: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("tags")
    val tags: Tags? = null,

    @field:SerializedName("partner_icon")
    val partnerIcon: Any? = null,

    @field:SerializedName("report_data")
    val reportData: ReportData? = null,

    @field:SerializedName("pkey")
    val pkey: String? = null,

    @field:SerializedName("text")
    val text: String? = null,

    @field:SerializedName("partner_code")
    val partnerCode: Any? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("coordinates")
    val coordinates: List<Any?>? = null
)