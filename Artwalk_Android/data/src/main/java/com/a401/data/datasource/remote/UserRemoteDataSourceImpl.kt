package com.a401.data.datasource.remote

import android.content.Context
import android.content.SharedPreferences
import com.a401.data.api.ApiClient
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    @ApplicationContext context: Context
) : UserRemoteDataSource {

    private val a401UserApi = ApiClient.getUserServerApiService()
    private val prefs: SharedPreferences = context.getSharedPreferences("a401Token", Context.MODE_PRIVATE)
    override suspend fun postIdToken(idToken: String) {
        val responseHeader = a401UserApi.postIdToken(idToken, "kakao").headers()
        val accessToken = responseHeader.value(5)
        val refreshToken = responseHeader.value(6)
        prefs.edit().putString("accessToken", accessToken).apply()
        prefs.edit().putString("refreshToken", refreshToken).apply()
        return
    }

}