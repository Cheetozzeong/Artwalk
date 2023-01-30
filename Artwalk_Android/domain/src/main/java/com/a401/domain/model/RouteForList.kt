package com.a401.domain.model

import java.util.*

data class RouteForList(
    val thumbnailUrl: String,
    val creation: Date,
    val title: String,
    val geometry: String,
    val routeId: Int
)
