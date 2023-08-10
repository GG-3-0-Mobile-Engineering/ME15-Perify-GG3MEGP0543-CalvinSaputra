package com.gg3megp0543.perify.core.utils

import com.gg3megp0543.perify.core.data.source.local.entity.DisasterEntity
import com.gg3megp0543.perify.core.data.source.remote.response.PropertiesResponse
import com.gg3megp0543.perify.core.domain.model.Disaster

object DataMapper {
    fun mapResponseToEntities(input: List<PropertiesResponse>): List<DisasterEntity> {
        val disasterList = ArrayList<DisasterEntity>()
        input.map {
            val disaster = DisasterEntity(
                pkey = it.pkey,
                imageUrl = it.imageUrl,
                disasterType = it.disasterType,
                tags = it.tags,
                createdAt = it.createdAt,
                title = it.title,
                text = it.text,
                coordinates = it.coordinates
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
                tags = it.tags,
                createdAt = it.createdAt,
                title = it.title,
                text = it.text,
                coordinates = it.coordinates
            )
        }

    fun mapDomainToEntity(input: Disaster) = DisasterEntity(
        pkey = input.pkey,
        imageUrl = input.imageUrl,
        disasterType = input.disasterType,
        tags = input.tags,
        createdAt = input.createdAt,
        title = input.title,
        text = input.text,
        coordinates = input.coordinates
    )
}