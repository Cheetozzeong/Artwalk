package com.a401.data.api

import com.a401.data.model.response.RouteListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RouteApiService {

    @GET("mapbox/{profile}/{coordinates}")
    suspend fun getRoute(
        @Path("profile") profile: String,
        @Path("coordinates") coordinates: String,
        @Query("geometries") geometries: String,
        @Query("overview") overview: String,
        @Query("access_token") access_token: String
    ): RouteListResponse

}