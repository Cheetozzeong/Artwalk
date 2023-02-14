package com.a401.data.mapper

import com.a401.data.model.request.RecordRequest
import com.a401.domain.model.RecordForSave

fun recordRequestFromRecordForSave(recordForSave: RecordForSave): RecordRequest =
    with(recordForSave) {
        RecordRequest(
            duration.toDouble(),
            distance,
            title,
            geometry
        )
    }