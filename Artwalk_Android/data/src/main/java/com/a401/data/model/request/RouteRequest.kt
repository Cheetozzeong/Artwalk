package com.a401.data.model.request

data class RouteRequest(
    val duration: Double,
    val distance: Double,
    val title: String?,
    val geometry: String
)
