package com.a401.data.api

import com.a401.data.model.response.UserResponse
import com.a401.data.model.request.LoginUserRequest
import com.a401.data.model.request.ArtWalkRegistRequest
import retrofit2.Response
import retrofit2.http.*

interface UserApiService {

    @POST("auth/login/{serviceType}")
    suspend fun postIdToken(
        @Header("id-token") idToken: String,
        @Path("serviceType") serviceType: String,
    ): Response<Void>

    @POST("auth/connect")
    suspend fun postLogin(
        @Header("accessToken") accessToken: String,
        @Header("refreshToken") refreshToken: String
    ): Response<UserResponse>

    @POST("auth/login/artwalk")
    suspend fun postLoginInfo(
        @Body() user: LoginUserRequest
    ): Response<Void>

    @POST("auth/reg/artwalk")
    suspend fun registArtWalk(
        @Body() user: ArtWalkRegistRequest
    ): UserResponse

    @GET("user/info")
    suspend fun getUserInfo(
        @Header("accessToken") accessToken: String
    ): UserResponse
}