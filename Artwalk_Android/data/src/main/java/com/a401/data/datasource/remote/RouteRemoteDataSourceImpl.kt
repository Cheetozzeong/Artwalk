package com.a401.data.datasource.remote

import com.a401.data.BuildConfig
import com.a401.data.api.ApiClient
import com.a401.data.mapper.routeForDrawFromResponse
import com.a401.data.mapper.routeForListsFromResponses
import com.a401.data.mapper.routeRequestFromRouteForDraw
import com.a401.data.model.request.MarkerRequest
import com.a401.domain.model.RouteForDraw
import com.a401.domain.model.RouteForList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class RouteRemoteDataSourceImpl @Inject constructor(

) : RouteRemoteDataSource{

    private val mapboxApi = ApiClient.getMapboxDirectionsApiService()
    private val a401Api = ApiClient.getRouteServerApiService()

    override suspend fun getRouteData(profile: String, coordinates: ArrayList<MarkerRequest>, geometries: String, overview: String): RouteForDraw {
        return routeForDrawFromResponse(mapboxApi.getRoute(
            profile,
            coordinates.convertCoordinatesToString(),
            geometries,
            overview,
            access_token = BuildConfig.MAPBOX_ACCESS_TOKEN
        ))
    }

    // TODO: login구현 후 삭제하고 저장된 token 사용
    private val accessToken = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyMDA3YmFlQG5hdmVyLmNvbSIsImF1dGgiOiJST0xFX1VTRVIiLCJleHAiOjE2NzU2ODg4NTd9.cQ0ot0d3joap981k6XICgGHWMB-HoxGd17UUVQrNr-Rf-ckzA7Z6POf_dr-SrLKOnaOF8VGaY1Dwn0aiAVl42Q"

    override suspend fun getRouteList(user: Boolean): Flow<List<RouteForList>> {
        return flow {
            emit(routeForListsFromResponses(
                a401Api.getRouteList(
                    accessToken,
                    user
                )
            ))
        }
    }

    override suspend fun postRoute(routeForDraw: RouteForDraw) {
        return a401Api.postRoute(
            accessToken,
            routeRequestFromRouteForDraw(routeForDraw)
        )
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