package com.gg3megp0543.perify.core.domain.usecase

import com.gg3megp0543.perify.core.data.Resource
import com.gg3megp0543.perify.core.domain.model.Disaster
import kotlinx.coroutines.flow.Flow

interface DisasterUseCase {
    fun getAllDisaster(
        admin: String? = null,
        disaster: String? = null
    ): Flow<Resource<List<Disaster>>>
}