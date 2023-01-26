package com.a401.domain.model

data class RouteForDraw(
    val duration: Int,
    val distance: Double,
    val geometry: String,
) {
    constructor() : this(
        0,
        0.0,
        "",
    )
}