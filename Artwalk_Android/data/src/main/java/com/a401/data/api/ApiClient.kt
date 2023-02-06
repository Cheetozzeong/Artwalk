package com.a401.data.api

import com.a401.data.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    fun getMapboxDirectionsApiService(): MapboxDirectionsApiService =
        getInstance(BuildConfig.MAPBOX_DIRECTIONS_URL).create(MapboxDirectionsApiService::class.java)

    fun getRouteServerApiService(): RouteApiService =
        getInstance(BuildConfig.ROUTE_BASE_URL).create(RouteApiService::class.java)

    fun getUserServerApiService(): UserApiService =
        getInstance(BuildConfig.LOGIN_BASE_URL).create(UserApiService::class.java)


    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private fun getInstance(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}