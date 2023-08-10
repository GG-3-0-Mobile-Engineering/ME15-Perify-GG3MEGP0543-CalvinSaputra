package com.gg3megp0543.perify.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.gg3megp0543.perify.core.data.source.local.entity.DisasterEntity

@Dao
interface DisasterDao {
    @Query("SELECT * FROM disaster")
    fun getAllDisaster(): LiveData<List<DisasterEntity>>

    // TODO 1 (Add the insance region code too, use AND for the query!)
    @Query("SELECT * FROM disaster WHERE disaster_type = :disasterType")
    fun getDisasterBasedOnTypeAndLocation(disasterType: String): LiveData<List<DisasterEntity>>
}