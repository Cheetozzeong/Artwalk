package com.a401.data.repository

import android.util.Log
import com.a401.data.BuildConfig
import com.a401.domain.model.Marker
import com.a401.domain.model.Route
import com.a401.domain.repository.RouteRepository
import com.mapbox.api.directions.v5.DirectionsCriteria
import com.mapbox.api.directions.v5.MapboxDirections
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.api.directions.v5.models.RouteOptions
import com.mapbox.core.constants.Constants
import com.mapbox.geojson.Point
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class RouteRepositoryImpl @Inject constructor(

): RouteRepository {

    override fun getRoute(
        from: Marker,
        to: Marker,
    ): Route {
        var route = Route()
        getRouteBetweenTwoMarker(from, to, successsCallback = {
            route = it
        })
        return route
    }

    private fun getRouteBetweenTwoMarker(
        from: Marker,
        to: Marker,
        successsCallback: (Route) -> Unit
    ) {

        val client = MapboxDirections.builder()
            .routeOptions(
                RouteOptions.builder()
                    .coordinatesList(listOf(
                        Point.fromLngLat(from.longitude, from.latitude),
                        Point.fromLngLat(to.longitude, to.latitude)
                    ))
                    .profile(DirectionsCriteria.PROFILE_WALKING)
                    .overview(DirectionsCriteria.OVERVIEW_SIMPLIFIED)
                    .geometries(DirectionsCriteria.GEOMETRY_POLYLINE)
                    .build()
            )
            .accessToken(BuildConfig.MAPBOX_ACCESS_TOKEN)
            .build()

        client.enqueueCall(object: Callback<DirectionsResponse> {

            override fun onFailure(call: Call<DirectionsResponse?>?, throwable: Throwable) {
            }

            override fun onResponse(
                call: Call<DirectionsResponse>,
                response: Response<DirectionsResponse>,
            ) {
                if(response.body() == null) {
                    TODO("Not yet implemented")
                    return
                } else if(response.body()!!.routes().size < 1) {
                    return
                }

                response.body()!!.routes().get(0).let {
                    if (it != null) {
                        successsCallback(
                            Route(
                                it.duration(),
                                it.distance(),
                                it.geometry()?:""
                            )
                        )
                    }
                }
            }
        })
    }
}
