package com.a401.data.repository

import com.a401.data.datasource.remote.RouteRemoteDataSource
import com.a401.data.mapper.markerToRequest
import com.a401.data.model.request.MarkerRequest
import com.a401.domain.model.Marker
import com.a401.domain.model.RouteForDraw
import com.a401.domain.model.RouteForList
import com.a401.domain.repository.RouteRepository
import com.mapbox.api.directions.v5.DirectionsCriteria
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject


class RouteRepositoryImpl @Inject constructor(
    private val routeRemoteDataSource: RouteRemoteDataSource
): RouteRepository {

    override suspend fun getRouteForWalking(
        from: Marker,
        to: Marker,
    ): Result<RouteForDraw> = try {

        val coordinates: ArrayList<MarkerRequest> = ArrayList()
        coordinates.add(markerToRequest(from))
        coordinates.add(markerToRequest(to))

        Result.success(
            routeRemoteDataSource.getRouteData(
                DirectionsCriteria.PROFILE_WALKING,
                coordinates,
                DirectionsCriteria.GEOMETRY_POLYLINE,
                DirectionsCriteria.OVERVIEW_SIMPLIFIED
            )
        )
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getRouteForList(): Flow<List<RouteForList>> {
        return routeRemoteDataSource.getRouteList(true)
    }

    override suspend fun postRoute(route: RouteForDraw): Flow<String> {
        return flow {
            routeRemoteDataSource.postRoute(route).collect() { response ->
                if (response.isSuccessful) {
                    emit("SUCCESS")
                }else {
                    emit("FAIL")
                }
            }
        }
    }
}
