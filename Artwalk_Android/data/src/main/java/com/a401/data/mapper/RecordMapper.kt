package com.a401.data.mapper

import com.a401.data.model.request.PutTitleRequest
import com.a401.data.model.request.RecordRequest
import com.a401.data.model.response.RecordListResponse
import com.a401.domain.model.RecordForList
import com.a401.domain.model.RecordForPut
import com.a401.domain.model.RecordForSave
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

fun recordRequestFromRecordForSave(recordForSave: RecordForSave): RecordRequest =
    with(recordForSave) {
        RecordRequest(
            duration.toDouble(),
            distance * 1000,
            title,
            geometry
        )
    }

fun recordRequestForPutTitle(recordForPut: RecordForPut): PutTitleRequest =
    with(recordForPut){
        PutTitleRequest(
            title,
            recordId
        )
    }