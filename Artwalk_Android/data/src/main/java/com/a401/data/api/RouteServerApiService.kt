package com.a401.data.api

import com.a401.data.model.request.RouteRequest
import com.a401.data.model.response.RouteListResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface RouteServerApiService {

    suspend fun getRouteList(): RouteListResponse

    @POST("route/")
    suspend fun postRoute(
        @Header("accessToken") accessToken: String,
        @Body() routeRequest: RouteRequest
    )
}