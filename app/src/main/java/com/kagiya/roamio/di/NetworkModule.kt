package com.kagiya.roamio.di

import android.content.Context
import com.kagiya.roamio.api.OpenTripMapService
import com.kagiya.roamio.data.network.OpenTripMapRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
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
    ) : OpenTripMapRepository{
        return OpenTripMapRepository(service)
    }
}
