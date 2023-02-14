package com.a401.data.datasource.remote

import com.a401.data.model.response.DeleteResponse
import com.a401.data.model.response.RecordListResponse
import com.a401.data.model.response.RecordResponse
import com.a401.domain.model.RecordForList
import com.a401.domain.model.RecordForSave
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RecordRemoteDataSource {

    suspend fun postRecord(recordForSave: RecordForSave): Flow<Response<Void>>

    suspend fun getRecordList(user: Boolean): Flow<List<RecordForList>>

    suspend fun getRecordCount(): Flow<RecordListResponse>

    suspend fun getEditLink(recordId: Int): Flow<RecordResponse>

    suspend fun deleteRecord(recordId: Int): Flow<DeleteResponse>
}