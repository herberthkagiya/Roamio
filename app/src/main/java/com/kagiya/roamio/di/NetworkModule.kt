package com.kagiya.roamio.di

import com.kagiya.roamio.api.OpenTripMapService
import com.kagiya.roamio.data.network.PlacesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOpenTripMapService() : OpenTripMapService{
        return OpenTripMapService.create()
    }

    @Provides
    @Singleton
    fun provideOpenTripMapRepository(
        service: OpenTripMapService
    ) : PlacesRepository{
        return PlacesRepository(service)
    }
}
