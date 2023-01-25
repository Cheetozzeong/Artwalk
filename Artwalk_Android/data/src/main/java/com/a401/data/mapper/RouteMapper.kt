package com.a401.data.mapper

import com.a401.data.model.response.RouteListResponse
import com.a401.domain.model.Route

fun routeDataFromResponse(response: RouteListResponse): Route =
    with(response.routes[0]) {
        Route(
            duration,
            distance,
            geometry
        )
    }
