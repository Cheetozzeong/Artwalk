package com.a401.data.model.request

data class RecordRequest(
    val duration: Double,
    val distance: Double,
    val title: String?,
    val geometry: String
)
