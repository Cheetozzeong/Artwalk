package com.a401.data.api

import com.a401.data.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiClient {

    fun getMapboxDirectionsApiService(): MapboxDirectionsApiService = getInstance(BuildConfig.MAPBOX_DIRECTIONS_URL).create(MapboxDirectionsApiService::class.java)
    fun getRouteServerApiService(): RouteServerApiService = getInstance("https://i8a401.p.ssafy.io:8080/").create(RouteServerApiService::class.java)

    private fun getInstance(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}