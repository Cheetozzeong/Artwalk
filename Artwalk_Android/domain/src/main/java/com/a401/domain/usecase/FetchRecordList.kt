package com.a401.domain.usecase

import com.a401.domain.model.RecordForList
import com.a401.domain.repository.RecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchRecordList @Inject constructor(
    private val recordRepository: RecordRepository
) {
    suspend operator fun invoke(): Flow<List<RecordForList>> {
        return recordRepository.getRecordForList()
    }
}