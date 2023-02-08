package com.a401.data.datasource.remote

import android.content.Context
import android.content.SharedPreferences
import com.a401.data.BuildConfig
import com.a401.data.api.ApiClient
import com.a401.data.mapper.routeForDrawFromResponse
import com.a401.data.mapper.routeForListsFromResponses
import com.a401.data.mapper.routeRequestFromRouteForDraw
import com.a401.data.model.request.MarkerRequest
import com.a401.data.model.response.RouteListResponse
import com.a401.domain.model.RouteForDraw
import com.a401.domain.model.RouteForList
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RouteRemoteDataSourceImpl @Inject constructor(
    @ApplicationContext context: Context
) : RouteRemoteDataSource{

    private val prefs: SharedPreferences = context.getSharedPreferences("a401Token", Context.MODE_PRIVATE)
    private val accessToken: String = "Bearer ${prefs.getString("accessToken", "")}"
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

    override suspend fun getRouteCount(): RouteListResponse {
        return a401Api.getRouteCount(accessToken)
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