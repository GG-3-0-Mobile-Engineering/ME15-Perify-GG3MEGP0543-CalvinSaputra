package com.gg3megp0543.perify.core.domain.model


data class Disaster(
    val pkey: String?,
    val imageUrl: Any?,
    val disasterType: String?,
    val location: String?,
    val createdAt: String?,
    val title: String?,
    val text: String?,
    val latitude: Double?,
    val longitude: Double?
)