package com.a401.domain.usecase

import com.a401.domain.model.RecordForPut
import com.a401.domain.repository.RecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PutRecordTitle @Inject constructor(
    private val recordRepository: RecordRepository
) {
    suspend operator fun invoke(recordForPut: RecordForPut): Flow<String> {
        return recordRepository.putRecordTitle(recordForPut)
    }
}