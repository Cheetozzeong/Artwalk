package com.a401.domain.usecase

import com.a401.domain.model.EditRecord
import com.a401.domain.repository.RecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEditLinkUseCase @Inject constructor(
    private val recordRepository: RecordRepository
) {
    suspend operator fun invoke(recordId: Int): Flow<EditRecord> {
        return recordRepository.getEditLink(recordId)
    }
}