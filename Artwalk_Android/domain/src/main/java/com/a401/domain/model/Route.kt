package com.a401.domain.model

data class Route(
    val duration: Int,
    val distance: Float,
    val geometry: String,
) {
    constructor() : this(
        0,
        0.0f,
        ""
    )
}