package com.a401.data.api

import com.a401.data.model.request.RecordRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface RecordApiService {

    @POST(".")
    suspend fun postRecord(
        @Header("accessToken") accessToken: String,
        @Body() record: RecordRequest
    ): Response<Void>
}