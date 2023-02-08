package com.a401.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun postIdToken(idToken: String): Flow<String>
}