package com.gg3megp0543.perify.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gg3megp0543.perify.core.data.source.local.entity.DisasterEntity

@Database(entities = [DisasterEntity::class], version = 1, exportSchema = false)
abstract class DisasterDatabase : RoomDatabase() {

    abstract fun disasterDao(): DisasterDao
}