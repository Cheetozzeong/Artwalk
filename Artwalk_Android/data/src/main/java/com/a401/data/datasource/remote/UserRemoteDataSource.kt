package com.a401.data.datasource.remote

import kotlinx.coroutines.flow.Flow


interface UserRemoteDataSource {
    suspend fun postIdToken(idToken: String): Flow<String>
}