package com.a401.data.api

import com.a401.data.model.request.RouteRequest
import com.a401.data.model.response.RouteListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface RouteApiService {

    @GET("list")
    suspend fun getRouteList(
        @Header("accessToken") accessToken: String,
        @Query("user") user: Boolean
    ): RouteListResponse

    @POST()
    suspend fun postRoute(
        @Header("accessToken") accessToken: String,
        @Body() route: RouteRequest
    )
}