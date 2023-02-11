package com.a401.domain.model

import java.util.*

data class RecordForList(
    val thumbnailUrl: String,
    val creation: Date,
    val detail: String,
    val geometry: String,
    val recordId: Int
)
