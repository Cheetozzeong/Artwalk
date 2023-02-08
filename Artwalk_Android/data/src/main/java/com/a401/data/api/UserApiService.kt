package com.a401.data.api

import kotlinx.coroutines.flow.Flow
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

}