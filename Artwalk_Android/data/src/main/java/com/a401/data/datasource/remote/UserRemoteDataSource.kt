package com.a401.data.datasource.remote


interface UserRemoteDataSource {
    suspend fun postIdToken(idToken: String)
}