package com.a401.data.datasource.remote

import com.a401.domain.model.RecordForSave
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RecordRemoteDataSource {
    suspend fun postRecord(recordForSave: RecordForSave): Flow<Response<Void>>
}