package com.a401.data.mapper

import com.a401.data.model.response.RouteListResponse
import com.a401.domain.model.RouteForDraw
import com.a401.domain.model.RouteForList
import java.util.*

fun routeForDrawFromResponse(response: RouteListResponse): RouteForDraw =
    with(response.routes[0]) {
        RouteForDraw(
            duration?.toInt() ?: 0,
            distance ?: 0.0,
            geometry ?: ""
        )
    }

fun routeForListsFromResponses(response: RouteListResponse): List<RouteForList> =
        response.routes.map { routes ->
            with(routes) {
                RouteForList(
                    thumbnail?:"",
                    creation?: Date(),
                    title?:"",
                    geometry?:"",
                    routeId?:-1
                )
            }
        }


