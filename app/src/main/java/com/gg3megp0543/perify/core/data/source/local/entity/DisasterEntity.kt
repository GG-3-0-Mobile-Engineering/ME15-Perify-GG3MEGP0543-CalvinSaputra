package com.gg3megp0543.perify.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gg3megp0543.perify.core.data.source.remote.response.Tags
@Entity(tableName = "disaster")
data class DisasterEntity(
    @PrimaryKey
    @ColumnInfo(name = "pkey")
    var pkey: String? = null,

    @ColumnInfo(name = "image_url")
    var imageUrl: Any? = null,

    @ColumnInfo(name = "disaster_type")
    var disasterType: String? = null,

    @ColumnInfo(name = "tags")
    var tags: Tags? = null,

    @ColumnInfo(name = "created_at")
    var createdAt: String? = null,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "text")
    var text: String? = null,

    @ColumnInfo(name = "coordinates")
    var coordinates: List<Any?>? = null
)