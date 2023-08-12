package com.gg3megp0543.perify.core.utils

import com.gg3megp0543.perify.core.data.source.local.entity.DisasterEntity
import com.gg3megp0543.perify.core.data.source.remote.response.GeometriesItem
import com.gg3megp0543.perify.core.domain.model.Disaster

object DataMapper {
    fun mapResponseToEntities(input: List<GeometriesItem?>): List<DisasterEntity> {
        val disasterList = ArrayList<DisasterEntity>()
        input.map {
            val disaster = DisasterEntity(
                pkey = it?.properties?.pkey.orEmpty(),
                imageUrl = it?.properties?.imageUrl.toString(),
                disasterType = it?.properties?.disasterType,
                location = it?.properties?.tags?.instanceRegionCode,
                createdAt = it?.properties?.createdAt,
                title = it?.properties?.title,
                text = it?.properties?.text,
                latitude = it?.coordinates?.get(1) ?: 0.0,
                longitude = it?.coordinates?.get(0) ?: 0.0
            )
            disasterList.add(disaster)
        }
        return disasterList
    }

    fun mapEntitiesToDomain(input: List<DisasterEntity>): List<Disaster> =
        input.map {
            Disaster(
                pkey = it.pkey,
                imageUrl = it.imageUrl,
                disasterType = it.disasterType,
                latitude = it.latitude,
                longitude = it.longitude,
                createdAt = it.createdAt,
                title = it.title,
                text = it.text,
                location = it.location
            )
        }
}