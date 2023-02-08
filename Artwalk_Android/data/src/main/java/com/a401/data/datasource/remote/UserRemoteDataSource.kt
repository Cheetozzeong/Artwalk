package com.a401.data.datasource.remote

import com.a401.domain.model.User
import kotlinx.coroutines.flow.Flow
import retrofit2.Response


interface UserRemoteDataSource {
    suspend fun postIdToken(idToken: String)
    suspend fun postLogin(accessToken: String, refreshToken: String)
    suspend fun postRegist(user: User, password: String): Flow<Response<Void>>
}