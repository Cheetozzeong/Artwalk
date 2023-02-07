package com.a401.data.datasource.remote

import android.content.Context
import android.content.SharedPreferences
import com.a401.data.api.ApiClient
import com.a401.data.model.response.UserResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Response
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    @ApplicationContext context: Context
) : UserRemoteDataSource {

    private val a401UserApi = ApiClient.getUserServerApiService()
    private val prefs: SharedPreferences = context.getSharedPreferences("a401Token", Context.MODE_PRIVATE)
    override suspend fun postIdToken(idToken: String) {
        val responseHeader = a401UserApi.postIdToken(idToken, "kakao").headers()
        val accessToken = responseHeader["accessToken"]
        val refreshToken = responseHeader["refreshToken"]
        prefs.edit().putString("accessToken", accessToken).apply()
        prefs.edit().putString("refreshToken", refreshToken).apply()
        return
    }

    override suspend fun postLogin(accessToken: String, refreshToken: String) {
        val responseHeader = a401UserApi.postLogin(accessToken, refreshToken).headers()
        val renewAccessToken = responseHeader["accessToken"]    
        prefs.edit().putString("accessToken", renewAccessToken).apply()
        return
    }


}