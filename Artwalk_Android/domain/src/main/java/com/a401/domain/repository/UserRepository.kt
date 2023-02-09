package com.a401.domain.repository

import com.a401.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun postIdToken(idToken: String)

    suspend fun postLogin(accessToken: String, refreshToken: String)

    suspend fun postRegist(user: User, password: String): Flow<String>
}