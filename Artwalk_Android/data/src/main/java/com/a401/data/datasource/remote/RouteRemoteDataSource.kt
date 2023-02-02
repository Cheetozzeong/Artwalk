package com.a401.data.datasource.remote

import com.a401.data.model.request.MarkerRequest
import com.a401.domain.model.RouteForDraw
import com.a401.domain.model.RouteForList
import kotlinx.coroutines.flow.Flow

interface RouteRemoteDataSource {

    suspend fun getRouteData(profile: String, coordinates: ArrayList<MarkerRequest>, geometries: String, overview: String): RouteForDraw

    suspend fun getRouteList(): Flow<List<RouteForList>>

    suspend fun postRoute(routeForDraw: RouteForDraw)
}