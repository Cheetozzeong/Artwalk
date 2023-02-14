package com.a401.domain.model

data class RecordForSave(
    val duration: Int,
    val distance: Double,
    val title: String?,
    val geometry: String
) {
    constructor() : this(
        0,
        0.0,
        "",
        "",
    )
}