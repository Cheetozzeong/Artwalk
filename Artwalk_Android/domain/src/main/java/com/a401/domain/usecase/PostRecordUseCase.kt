package com.a401.domain.usecase

import com.a401.domain.model.RecordForSave
import com.a401.domain.repository.RecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRecordUseCase @Inject constructor(
    private val recordRepository: RecordRepository
) {
    suspend operator fun invoke(recordForSave: RecordForSave): Flow<String> {
        return recordRepository.postRecord(recordForSave)
    }
}