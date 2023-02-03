package com.a401.data.datasource.remote

import com.a401.data.BuildConfig
import com.a401.data.api.ApiClient
import com.a401.data.mapper.routeForDrawFromResponse
import com.a401.data.mapper.routeForListsFromResponses
import com.a401.data.model.request.MarkerRequest
import com.a401.domain.model.RouteForDraw
import com.a401.domain.model.RouteForList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RouteRemoteDataSourceImpl @Inject constructor(
) : RouteRemoteDataSource{

    private val mapboxApi = ApiClient.getMapboxDirectionsApiService()
//    private val routeServerApi = ApiClient.getRouteServerApiService()

    override suspend fun getRouteData(profile: String, coordinates: ArrayList<MarkerRequest>, geometries: String, overview: String): RouteForDraw {
        return routeForDrawFromResponse(mapboxApi.getRoute(
            profile,
            coordinates.convertCoordinatesToString(),
            geometries,
            overview,
            access_token = BuildConfig.MAPBOX_ACCESS_TOKEN
        ))
    }

    override suspend fun getRouteList(): Flow<List<RouteForList>> {
//        return flow {
//            emit(routeForListsFromResponses(routeServerApi.getRouteList()))
//        }
        return flow { emit(emptyList<RouteForList>()) }
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