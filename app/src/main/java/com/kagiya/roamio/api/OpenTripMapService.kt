package com.kagiya.roamio.api

import com.kagiya.roamio.BuildConfig
import com.kagiya.roamio.data.network.PlaceId
import com.kagiya.roamio.data.network.PlaceDetails
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface OpenTripMapService {

    @GET("places/radius")
    suspend fun fetchRecommendedPlacesIds(
        @Query("radius") radius: Int,
        @Query("lon") longitude: String,
        @Query("lat") latitude: String,
        @Query("rate") rate: String,
        @Query("format") format: String,
        @Query("limit") limit: Int
    ): List<PlaceId>


    @GET ("places/xid/{id}")
    suspend fun getPlaceDetails(@Path("id") placeId: String) : PlaceDetails



    companion object{

        private const val BASE_URL = "https://api.opentripmap.com/0.1/en/"
        private const val OPEN_TRIP_MAP_API_KEY = BuildConfig.OPEN_TRIP_MAP_API_KEY


        private val apiKeyInterceptor = Interceptor { chain ->

            val originalRequest = chain.request()

            val newUrl = originalRequest.url
                .newBuilder()
                .addQueryParameter("apikey", OPEN_TRIP_MAP_API_KEY)
                .build()

            val newRequest = originalRequest.newBuilder()
                .url(newUrl)
                .build()

            chain.proceed(newRequest)
        }

        private val loggerInterceptor = HttpLoggingInterceptor().apply { level = Level.BODY}

        private val client = OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(loggerInterceptor)
            .build()


        fun create() : OpenTripMapService {

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(client)
                .build()
                .create(OpenTripMapService::class.java)
        }
    }
}



