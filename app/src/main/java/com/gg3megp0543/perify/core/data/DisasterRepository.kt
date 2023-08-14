package com.gg3megp0543.perify.core.data

import com.gg3megp0543.perify.core.data.source.local.LocalDataSource
import com.gg3megp0543.perify.core.data.source.remote.RemoteDataSource
import com.gg3megp0543.perify.core.data.source.remote.network.ApiResponse
import com.gg3megp0543.perify.core.data.source.remote.response.GeometriesItem
import com.gg3megp0543.perify.core.domain.model.Disaster
import com.gg3megp0543.perify.core.domain.repository.IDisasterRepository
import com.gg3megp0543.perify.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DisasterRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : IDisasterRepository {

    override fun getAllDisaster(admin: String?, disaster: String?): Flow<Resource<List<Disaster>>> =
        object : NetworkBoundResource<List<Disaster>, List<GeometriesItem?>>() {
            override fun loadFromDB(): Flow<List<Disaster>> {
                return localDataSource.getAllDisaster(location = admin, disasterType = disaster)
                    .map {
                        DataMapper.mapEntitiesToDomain(it)
                    }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<GeometriesItem?>>> =
                remoteDataSource.getAllDisaster(admin, disaster)

            override suspend fun saveCallResult(data: List<GeometriesItem?>) {
                val disasterList = DataMapper.mapResponseToEntities(data)
                localDataSource.insertDisaster(disasterList)
            }

            override fun shouldFetch(data: List<Disaster>?): Boolean =
                data.isNullOrEmpty()

        }.asFlow()
}
