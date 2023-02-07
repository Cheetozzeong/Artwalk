package com.a401.data.api

import com.a401.data.model.response.UserResponse
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApiService {

    @POST("login/{serviceType}")
    suspend fun postIdToken(
        @Header("id-token") idToken: String,
        @Path("serviceType") serviceType: String
    ): Response<Void>

    @POST("connect")
    suspend fun postLogin(
        @Header("accessToken") accessToken: String,
        @Header("refreshToken") refreshToken: String
    ): Response<UserResponse>
}