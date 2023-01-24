package com.a401.data.api

import com.a401.data.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    fun getRouteApiService(): RouteApiService = getInstance(BuildConfig.MAPBOX_DIRECTIONS_URL).create(RouteApiService::class.java)

    private fun getInstance(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}