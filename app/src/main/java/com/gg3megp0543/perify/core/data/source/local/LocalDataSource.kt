package com.gg3megp0543.perify.core.data.source.local

import androidx.lifecycle.LiveData
import com.gg3megp0543.perify.core.data.source.local.entity.DisasterEntity
import com.gg3megp0543.perify.core.data.source.local.room.DisasterDao

class LocalDataSource private constructor(private val disasterDao: DisasterDao) {
    companion object {
        private var instance: LocalDataSource? = null

        fun getInstance(disasterDao: DisasterDao): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(disasterDao)
            }
    }

    fun getAllDisaster(): LiveData<List<DisasterEntity>> = disasterDao.getAllDisaster()

    // TODO 2 (Add the query for fetching specific data with disaster_type and region_code)
}