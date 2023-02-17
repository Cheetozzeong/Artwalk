package com.a401.domain.model

import java.util.*
import java.io.Serializable

data class RecordForList(
    val thumbnailUrl: String,
    val duration: Int,
    val distance: Double,
    val creation: Date,
    val title: String,
    val recordId: Int
) : Serializable