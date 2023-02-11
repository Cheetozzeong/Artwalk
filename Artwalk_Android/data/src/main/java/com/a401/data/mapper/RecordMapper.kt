package com.a401.data.mapper

import com.a401.data.model.response.RecordListResponse
import com.a401.domain.model.RecordForList
import java.util.*

fun recordForListsFromResponses(response: RecordListResponse): List<RecordForList> =
    response.records.map { records ->
        with(records) {
            RecordForList(
                thumbnail?:"",
                creation?: Date(),
                detail?:"",
                geometry?:"",
                recordId?:-1
            )
        }
    }