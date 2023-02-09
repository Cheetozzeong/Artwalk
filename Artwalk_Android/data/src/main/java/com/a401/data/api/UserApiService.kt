package com.a401.data.api

import com.a401.data.model.request.LoginUserRequest
import com.a401.data.model.request.ArtWalkRegistRequest
import com.a401.data.model.response.UserResponse
import retrofit2.Response
import retrofit2.http.*

interface UserApiService {

    @POST("auth/login/{serviceType}")
    suspend fun postIdToken(
        @Header("id-token") idToken: String,
        @Path("serviceType") serviceType: String,
    ): Response<Void>

    @POST("auth/login/artwalk")
    suspend fun postLoginInfo(
        @Body() user: LoginUserRequest
    ): Response<Void>

    @POST("auth/reg/artwalk")
    suspend fun registArtWalk(
        @Body() user: ArtWalkRegistRequest
    ): Response<Void>

    @GET("user/info")
    suspend fun getUserInfo(
        @Header("accessToken") accessToken: String
    ): UserResponse
}