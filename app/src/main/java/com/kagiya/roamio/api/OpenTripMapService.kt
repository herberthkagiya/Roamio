package com.kagiya.roamio.api

import com.kagiya.roamio.data.network.Place
import com.kagiya.roamio.data.network.RecommendedPlacesResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface OpenTripMapService {

    @GET("places/radius")
    suspend fun getRecommendedPlaces(
        @Query("radius") radius: Int,
        @Query("lon") longitude: String,
        @Query("lat") latitude: String,
        @Query("format") format: String,
        @Query("limit") limit: Int
    ): List<Place>


    companion object{
        val language = "en"
        val BASE_URL = "https://api.opentripmap.com/0.1/en/"


        private val apiKeyInterceptor = Interceptor { chain ->

            val originalRequest = chain.request()

            val newUrl = originalRequest.url
                .newBuilder()
                .addQueryParameter("apikey", "5ae2e3f221c38a28845f05b62e89bd3f0e62c18988ba8d6665dd4367")
                .build()

            val newRequest = originalRequest.newBuilder()
                .url(newUrl)
                .build()

            chain.proceed(newRequest)
        }

        val loggerInterceptor = HttpLoggingInterceptor().apply { level = Level.BODY}

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



