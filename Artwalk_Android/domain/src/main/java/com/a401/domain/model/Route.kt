package com.a401.domain.model

data class Route(
    val duration: Double,
    val distance: Double,
    val lineString: String,
) {
    constructor() : this(
        0.0,
        0.0,
        ""
    )
}