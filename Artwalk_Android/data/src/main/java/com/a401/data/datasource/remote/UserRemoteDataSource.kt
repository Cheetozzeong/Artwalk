package com.a401.data.datasource.remote

import com.a401.data.model.request.LoginUserRequest
import kotlinx.coroutines.flow.Flow


interface UserRemoteDataSource {

    suspend fun postIdToken(idToken: String, serviceType: String): Flow<String>

    suspend fun postLoginInfo(loginInfo: LoginUserRequest): Flow<String>
}