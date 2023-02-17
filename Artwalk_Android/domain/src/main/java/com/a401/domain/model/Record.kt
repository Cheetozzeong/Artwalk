package com.a401.domain.model

data class Record(
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