package com.a401.data.api

import com.a401.data.model.request.RecordRequest
import com.a401.data.model.response.RecordListResponse
import com.a401.data.model.response.RouteListResponse
import retrofit2.Response
import retrofit2.http.*

interface RecordApiService {

    @POST(".")
    suspend fun postRecord(
        @Header("accessToken") accessToken: String,
        @Body() record: RecordRequest
    ): Response<Void>

    @GET("list")
    suspend fun getRecordList(
        @Header("accessToken") accessToken: String,
        @Query("user") user: Boolean
    ): RecordListResponse

    @GET("count")
    suspend fun getRecordCount(
        @Header("accessToken") accessToken: String,
    ): RecordListResponse
}