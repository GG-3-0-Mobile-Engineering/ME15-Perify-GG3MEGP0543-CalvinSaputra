package com.gg3megp0543.perify.logic.model

import com.google.gson.annotations.SerializedName

data class DisasterReportResponse(

    @field:SerializedName("result")
    val result: Result? = null,

    @field:SerializedName("statusCode")
    val statusCode: Int? = null
)

data class GeometriesItem(

    @field:SerializedName("coordinates")
    val coordinates: List<Any?>? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("properties")
    val properties: Properties? = null
)

data class Tags(

    @field:SerializedName("instance_region_code")
    val instanceRegionCode: String? = null,

    @field:SerializedName("district_id")
    val districtId: Any? = null,

    @field:SerializedName("local_area_id")
    val localAreaId: Any? = null,

    @field:SerializedName("region_code")
    val regionCode: String? = null
)

data class FireRadius(

    @field:SerializedName("lng")
    val lng: Any? = null,

    @field:SerializedName("lat")
    val lat: Any? = null
)

data class Properties(

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

data class Output(

    @field:SerializedName("geometries")
    val geometries: List<GeometriesItem?>? = null,

    @field:SerializedName("type")
    val type: String? = null
)

data class Result(

    @field:SerializedName("objects")
    val objects: Objects? = null,

    @field:SerializedName("bbox")
    val bbox: List<Any?>? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("arcs")
    val arcs: List<Any?>? = null
)

data class ReportData(

    @field:SerializedName("flood_depth")
    val floodDepth: Int? = null,

    @field:SerializedName("report_type")
    val reportType: String? = null,

    @field:SerializedName("structureFailure")
    val structureFailure: Int? = null,

    @field:SerializedName("personLocation")
    val personLocation: PersonLocation? = null,

    @field:SerializedName("fireLocation")
    val fireLocation: FireLocation? = null,

    @field:SerializedName("fireRadius")
    val fireRadius: FireRadius? = null,

    @field:SerializedName("fireDistance")
    val fireDistance: Any? = null,

    @field:SerializedName("airQuality")
    val airQuality: Int? = null,

    @field:SerializedName("visibility")
    val visibility: Int? = null,

    @field:SerializedName("impact")
    val impact: Int? = null,

    @field:SerializedName("volcanicSigns")
    val volcanicSigns: List<Int?>? = null,

    @field:SerializedName("evacuationArea")
    val evacuationArea: Boolean? = null,

    @field:SerializedName("evacuationNumber")
    val evacuationNumber: Int? = null
)

data class FireLocation(

    @field:SerializedName("lng")
    val lng: Any? = null,

    @field:SerializedName("lat")
    val lat: Any? = null
)

data class PersonLocation(

    @field:SerializedName("lng")
    val lng: Any? = null,

    @field:SerializedName("lat")
    val lat: Any? = null
)

data class Objects(

    @field:SerializedName("output")
    val output: Output? = null
)
