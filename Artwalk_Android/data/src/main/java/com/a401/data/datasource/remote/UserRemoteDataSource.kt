package com.a401.data.datasource.remote


interface UserRemoteDataSource {
    suspend fun postIdToken(idToken: String)
    suspend fun postLogin(accessToken: String, refreshToken: String): Boolean
}