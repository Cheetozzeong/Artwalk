package com.a401.data.mapper

import com.a401.data.model.response.RouteListResponse
import com.a401.domain.model.RouteForDraw

fun routeDataFromResponse(response: RouteListResponse): RouteForDraw =
    with(response.routes[0]) {
        RouteForDraw(
            (duration ?: 0) as Int,
            (distance ?: 0) as Float,
            geometry ?: ""
        )
    }
