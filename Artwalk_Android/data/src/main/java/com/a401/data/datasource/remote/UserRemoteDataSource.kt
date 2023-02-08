package com.a401.data.datasource.remote

import com.a401.data.model.response.UserResponse
import com.a401.data.model.request.LoginUserRequest
import com.a401.domain.model.User
import kotlinx.coroutines.flow.Flow
import retrofit2.Response


interface UserRemoteDataSource {

    suspend fun postIdToken(idToken: String, serviceType: String): Flow<String>

    suspend fun postLoginInfo(loginInfo: LoginUserRequest): Flow<String>

    suspend fun postRegist(user: User, password: String): Flow<Response<Void>>

    suspend fun getUserInfo(): Flow<UserResponse?>
}