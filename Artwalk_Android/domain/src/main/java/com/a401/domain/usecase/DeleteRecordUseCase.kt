package com.a401.domain.usecase

import com.a401.domain.model.DeleteRecord
import com.a401.domain.repository.RecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteRecordUseCase @Inject constructor(
    private val recordRepository: RecordRepository
) {
    suspend operator fun invoke(recordId: Int): Flow<DeleteRecord> {
        return recordRepository.deleteRecord(recordId)
    }
}