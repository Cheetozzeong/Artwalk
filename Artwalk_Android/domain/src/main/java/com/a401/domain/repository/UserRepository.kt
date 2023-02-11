package com.a401.domain.repository

import kotlinx.coroutines.flow.Flow
import com.a401.domain.model.User

interface UserRepository {

    suspend fun postIdToken(idToken: String, serviceType: String): Flow<String>

    suspend fun postLoginInfo(userId: String, password: String): Flow<String>

    suspend fun postLogin(accessToken: String, refreshToken: String)

    suspend fun postRegist(user: User, password: String): Flow<String>

    suspend fun getUserInfo(): Flow<User>
}