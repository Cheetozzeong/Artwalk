package com.a401.data.api

import com.a401.data.model.request.RecordRequest
import com.a401.data.model.response.DeleteResponse
import com.a401.data.model.response.RecordListResponse
import com.a401.data.model.response.RecordResponse
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

    @GET("share/{recordId}")
    suspend fun getEditLink(
        @Header("accessToken") accessToken: String,
        @Path("recordId") recordId: Int
    ): RecordResponse

    @DELETE("{recordId}")
    suspend fun deleteRecord(
        @Header("accessToken") accessToken: String,
        @Path("recordId") recordId: Int
    ): DeleteResponse

    @PUT("{recordId}")
    suspend fun putRecordTitle(
        @Header("accessToken") accessToken: String,
        @Path("recordId") recordId: Int,
        @Body() title: String?
    ): RecordListResponse
}