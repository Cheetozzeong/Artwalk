package com.a401.data.mapper

import com.a401.data.model.response.RouteListForDrawResponse
import com.a401.domain.model.RouteForDraw

fun routeDataFromResponse(response: RouteListForDrawResponse): RouteForDraw =
    with(response.routes[0]) {
        RouteForDraw(
            duration.toInt(),
            distance.toFloat(),
            geometry
        )
    }
