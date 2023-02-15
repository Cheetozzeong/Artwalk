package com.a401.data.repository

import com.a401.data.datasource.remote.RecordRemoteDataSource
import com.a401.domain.model.*
import com.a401.domain.repository.RecordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecordRepositoryImpl @Inject constructor(
    private val recordRemoteDataSource: RecordRemoteDataSource
): RecordRepository {
    override suspend fun postRecord(record: RecordForSave): Flow<String> {
        return flow {
            recordRemoteDataSource.postRecord(record).collect() { response ->
                if (response.isSuccessful) {
                    emit("SUCCESS")
                } else {
                    emit("FAIL")
                }
            }
        }
    }

    override suspend fun getRecordForList(): Flow<List<RecordForList>> {
        return recordRemoteDataSource.getRecordList(true)
    }

    override suspend fun getEditLink(recordId: Int): Flow<EditRecord> {
        return flow {
            recordRemoteDataSource.getEditLink(recordId).collect() { response ->
                    emit(EditRecord(response.code,response.message))
            }
        }
    }

    override suspend fun deleteRecord(recordId: Int): Flow<DeleteRecord> {
        return flow {
            recordRemoteDataSource.deleteRecord(recordId).collect() { response ->
                emit(DeleteRecord(response.code,response.count))
            }
        }
    }

    override suspend fun putRecordTitle(recordForPut: RecordForPut): Flow<String> {
        return flow {
            recordRemoteDataSource.putRecordDetail(recordForPut).collect() { response ->
                if (response.isSuccessful) {
                    emit("SUCCESS")
                } else {
                    emit("FAIL")
                }
            }
        }
    }
}