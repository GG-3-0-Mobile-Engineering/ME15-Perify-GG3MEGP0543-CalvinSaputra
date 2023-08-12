package com.gg3megp0543.perify.core.domain.repository

import com.gg3megp0543.perify.core.data.Resource
import com.gg3megp0543.perify.core.domain.model.Disaster
import kotlinx.coroutines.flow.Flow

interface IDisasterRepository {
    fun getAllDisaster(
        admin: String? = null,
        disaster: String? = null
    ): Flow<Resource<List<Disaster>>>
}