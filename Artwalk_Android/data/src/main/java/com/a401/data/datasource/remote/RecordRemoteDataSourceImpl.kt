package com.a401.data.datasource.remote

import android.content.Context
import android.content.SharedPreferences
import com.a401.data.api.ApiClient
import com.a401.data.mapper.recordForListsFromResponses
import com.a401.data.mapper.recordRequestFromRecordForSave
import com.a401.data.model.response.DeleteResponse
import com.a401.data.model.response.RecordListResponse
import com.a401.data.model.response.RecordResponse
import com.a401.domain.model.RecordForList
import com.a401.domain.model.RecordForSave
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class RecordRemoteDataSourceImpl @Inject constructor(
    @ApplicationContext context: Context
) : RecordRemoteDataSource {
    private val a401RecordApi = ApiClient.getRecordServerApiService()
    private val prefs: SharedPreferences = context.getSharedPreferences("a401Token", Context.MODE_PRIVATE)
    private val accessToken: String = "Bearer ${prefs.getString("accessToken", "")}"

    override suspend fun postRecord(recordForSave: RecordForSave): Flow<Response<Void>> {
        return flow {
            emit( a401RecordApi.postRecord(accessToken, recordRequestFromRecordForSave(recordForSave)))
        }
    }

    override suspend fun getRecordList(user: Boolean): Flow<List<RecordForList>> {
        return flow {
            emit(
                recordForListsFromResponses(
                    a401RecordApi.getRecordList(
                        accessToken,
                        user
                    )
                )
            )
        }
    }

    override suspend fun getRecordCount(): Flow<RecordListResponse> {
        return flow { emit(a401RecordApi.getRecordCount(accessToken)) }
    }

    override suspend fun getEditLink(recordId: Int): Flow<RecordResponse> {
        return flow{
            emit(a401RecordApi.getEditLink(accessToken, recordId))
        }
    }

    override suspend fun deleteRecord(recordId: Int): Flow<DeleteResponse> {
        return flow{
            emit(a401RecordApi.deleteRecord(accessToken, recordId))
        }
    }
}