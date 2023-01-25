package com.a401.data.datasource.remote

import com.a401.data.BuildConfig
import com.a401.data.api.ApiClient
import com.a401.data.mapper.routeDataFromResponse
import com.a401.data.model.request.MarkerRequest
import com.a401.domain.model.RouteForDraw
import javax.inject.Inject

class RouteRemoteDataSourceImpl @Inject constructor(
) : RouteRemoteDataSource{

    private val api = ApiClient.getRouteApiService()

    override suspend fun getRouteData(profile: String, coordinates: ArrayList<MarkerRequest>, geometries: String, overview: String): RouteForDraw {
        return routeDataFromResponse(api.getRoute(
            profile,
            coordinates.convertCoordinatesToString(),
            geometries,
            overview,
            access_token = BuildConfig.MAPBOX_ACCESS_TOKEN
        ))
    }

    private fun ArrayList<MarkerRequest>.convertCoordinatesToString(): String {
        val stringBuffer = StringBuffer()
        map { markerRequest ->
            stringBuffer.append(markerRequest.toString())
        }
        stringBuffer.deleteCharAt(stringBuffer.lastIndexOf(';'))
        return stringBuffer.toString()
    }
}