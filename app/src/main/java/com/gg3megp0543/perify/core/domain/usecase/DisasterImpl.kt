package com.gg3megp0543.perify.core.domain.usecase

import com.gg3megp0543.perify.core.data.Resource
import com.gg3megp0543.perify.core.domain.model.Disaster
import com.gg3megp0543.perify.core.domain.repository.IDisasterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DisasterImpl @Inject constructor(private val disasterRepository: IDisasterRepository) :
    DisasterUseCase {
    override fun getAllDisaster(
        admin: String?,
        disaster: String?
    ): Flow<Resource<List<Disaster>>> =
        disasterRepository.getAllDisaster(admin, disaster)
}