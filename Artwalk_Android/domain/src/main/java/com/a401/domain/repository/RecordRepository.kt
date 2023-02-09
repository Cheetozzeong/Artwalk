package com.a401.domain.repository

import com.a401.domain.model.RecordForSave
import kotlinx.coroutines.flow.Flow

interface RecordRepository {
    suspend fun postRecord(record: RecordForSave): Flow<String>
}