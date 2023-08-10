package com.gg3megp0543.perify.core.domain.model

import androidx.room.ColumnInfo
import com.gg3megp0543.perify.core.data.source.remote.response.Tags

data class Disaster(
    val pkey: String?,
    val imageUrl: Any?,
    val disasterType: String?,
    val tags: Tags?,
    val createdAt: String?,
    val title: String?,
    val text: String?,
    val coordinates: List<Any?>?
)