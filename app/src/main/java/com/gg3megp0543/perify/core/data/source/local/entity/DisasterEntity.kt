package com.gg3megp0543.perify.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "disaster")
data class DisasterEntity(
    @PrimaryKey
    @ColumnInfo(name = "pkey")
    var pkey: String,

    @ColumnInfo(name = "image_url")
    var imageUrl: String? = null,

    @ColumnInfo(name = "disaster_type")
    var disasterType: String? = null,

    @ColumnInfo(name = "location")
    var location: String? = null,

    @ColumnInfo(name = "created_at")
    var createdAt: String? = null,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "text")
    var text: String? = null,

    @ColumnInfo(name = "latidute")
    var latitude: Double = 0.0,

    @ColumnInfo(name = "longitude")
    val longitude: Double = 0.0
)