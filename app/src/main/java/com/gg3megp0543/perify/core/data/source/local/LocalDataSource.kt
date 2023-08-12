package com.gg3megp0543.perify.core.data.source.local

import com.gg3megp0543.perify.core.data.source.local.entity.DisasterEntity
import com.gg3megp0543.perify.core.data.source.local.room.DisasterDao
import com.gg3megp0543.perify.core.domain.model.Disaster
import kotlinx.coroutines.flow.Flow

class LocalDataSource private constructor(private val disasterDao: DisasterDao) {
    companion object {
        private var instance: LocalDataSource? = null

        fun getInstance(disasterDao: DisasterDao): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(disasterDao)
            }
    }

    fun getAllDisaster(
        location: String?, disasterType: String?
    ): Flow<List<DisasterEntity>> = disasterDao.getAllDisaster(location, disasterType)

    suspend fun insertDisaster(disasterList: List<DisasterEntity>) =
        disasterDao.insertDisaster(disasterList)
}