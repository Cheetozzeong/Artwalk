package com.a401.data.mapper

import com.a401.data.model.request.RouteRequest
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
                    thumbnail ?: "",
                    creation = creation.let {
                        val current = Calendar.getInstance()
                        current.time = Date()
                        val creation = Calendar.getInstance()
                        creation.time = it ?: Date()

                        val yearDiff = current.get(Calendar.YEAR) - creation.get(Calendar.YEAR)
                        val monthDiff = current.get(Calendar.MONTH) - creation.get(Calendar.MONTH)
                        val dayDiff = current.get(Calendar.DAY_OF_MONTH) - creation.get(Calendar.DAY_OF_MONTH)
                        val hourDiff = current.get(Calendar.HOUR_OF_DAY) - creation.get(Calendar.HOUR_OF_DAY)
                        val minuteDiff = current.get(Calendar.MINUTE) - creation.get(Calendar.MINUTE)
                        val secondDiff = current.get(Calendar.SECOND) - creation.get(Calendar.SECOND)

                        return@let when {
                            yearDiff > 0 -> "${yearDiff}년 전"
                            monthDiff > 0 -> "${monthDiff}달 전"
                            dayDiff > 0 -> "${dayDiff}일 전"
                            hourDiff > 0 -> "${hourDiff}시간 전"
                            minuteDiff > 0 -> "${minuteDiff}분 전"
                            else -> "${secondDiff}초 전"
                        }
                    },
                    title ?: "",
                    geometry ?: "",
                    routeId ?: -1,
                    distance = distance?.div(1000) ?: 0.0,
                    duration = duration.let{
                        val sb = StringBuilder()
                        val duration = it?.toInt() ?: 0
                        sb.append(duration / 3600)
                        sb.append("시 ")
                        sb.append(duration % 3600 / 60)
                        sb.append("분 ")
                        sb.append(duration % 60)
                        sb.append("초")
                        sb.toString()
                    }
                )
            }
        }

fun routeRequestFromRouteForDraw(routeForDraw: RouteForDraw): RouteRequest =
    with(routeForDraw) {
        RouteRequest(
            duration.toDouble(),
            distance,
            title = null,
            geometry
        )
    }

