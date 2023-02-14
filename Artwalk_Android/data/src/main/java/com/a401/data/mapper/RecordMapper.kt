package com.a401.data.mapper

import com.a401.data.model.response.RecordListResponse
import com.a401.domain.model.RecordForList
import java.util.*

fun recordForListsFromResponses(response: RecordListResponse): List<RecordForList> =
    response.records.map { records ->
        with(records) {
            RecordForList(
                thumbnail?:"",
                (duration?:0.0).toInt(),
                distance?:0.0,
                creation?: Date(),
                title?:"",
                recordId?:-1
            )
        }
    }