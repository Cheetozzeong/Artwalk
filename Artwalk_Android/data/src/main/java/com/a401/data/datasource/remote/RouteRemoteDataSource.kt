package com.a401.data.datasource.remote

import com.a401.data.model.request.MarkerRequest
import com.a401.domain.model.Route

interface RouteRemoteDataSource {

    suspend fun getRouteData(profile: String, coordinates: ArrayList<MarkerRequest>, geometries: String, overview: String): Route

}