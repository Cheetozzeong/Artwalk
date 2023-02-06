package com.a401.domain.repository

interface UserRepository {
    suspend fun postIdToken(idToken: String)
}