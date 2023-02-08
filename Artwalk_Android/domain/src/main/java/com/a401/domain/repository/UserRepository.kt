package com.a401.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun postIdToken(idToken: String, serviceType: String): Flow<String>

    suspend fun postLoginInfo(userId: String, password: String): Flow<String>
}