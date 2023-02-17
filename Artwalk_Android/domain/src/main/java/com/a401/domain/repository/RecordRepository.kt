package com.a401.domain.repository

import com.a401.domain.model.*
import kotlinx.coroutines.flow.Flow

interface RecordRepository {
    suspend fun postRecord(record: RecordForSave): Flow<String>

    suspend fun getRecordForList(): Flow<List<RecordForList>>

    suspend fun getEditLink(recordId: Int): Flow<EditRecord>

    suspend fun deleteRecord(recordId: Int): Flow<DeleteRecord>

    suspend fun putRecordTitle(recordForPut: RecordForPut): Flow<String>
}