package com.gg3megp0543.perify.core.data.source.local

import com.gg3megp0543.perify.core.data.source.local.entity.DisasterEntity
import com.gg3megp0543.perify.core.data.source.local.room.DisasterDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val disasterDao: DisasterDao) {
    fun getAllDisaster(
        location: String?, disasterType: String?
    ): Flow<List<DisasterEntity>> = disasterDao.getAllDisaster(location, disasterType)

    suspend fun insertDisaster(disasterList: List<DisasterEntity>) =
        disasterDao.insertDisaster(disasterList)
}