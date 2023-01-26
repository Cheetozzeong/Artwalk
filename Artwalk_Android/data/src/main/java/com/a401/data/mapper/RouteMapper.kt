package com.a401.data.mapper

import com.a401.data.model.response.RouteListResponse
import com.a401.domain.model.RouteForDraw

fun routeDataFromResponse(response: RouteListResponse): RouteForDraw =
    with(response.routes[0]) {
        RouteForDraw(
            duration?.toInt() ?: 0,
            distance ?: 0.0,
            geometry ?: ""
        )
    }
