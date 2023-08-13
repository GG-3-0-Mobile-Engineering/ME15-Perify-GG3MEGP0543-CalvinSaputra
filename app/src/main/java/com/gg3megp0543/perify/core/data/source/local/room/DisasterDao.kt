package com.gg3megp0543.perify.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gg3megp0543.perify.core.data.source.local.entity.DisasterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DisasterDao {
    @Query("SELECT * FROM disaster WHERE (:location IS NULL OR :location = :location) AND (:disasterType IS NULL OR disaster_type = :disasterType)")
    fun getAllDisaster(location: String?, disasterType: String?): Flow<List<DisasterEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDisaster(disaster: List<DisasterEntity>)
}