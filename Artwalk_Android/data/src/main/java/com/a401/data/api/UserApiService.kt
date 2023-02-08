package com.a401.data.api

import com.a401.data.model.request.LoginUserRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApiService {

    @POST("login/{serviceType}")
    suspend fun postIdToken(
        @Header("id-token") idToken: String,
        @Path("serviceType") serviceType: String,
    ): Response<Void>

    @POST("login/artwalk")
    suspend fun postLoginInfo(
        @Body() user: LoginUserRequest
    ): Response<Void>
}