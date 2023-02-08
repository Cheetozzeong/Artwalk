package com.a401.data.datasource.remote

import android.content.Context
import android.content.SharedPreferences
import com.a401.data.api.ApiClient
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    @ApplicationContext context: Context
) : UserRemoteDataSource {

    private val a401UserApi = ApiClient.getUserServerApiService()
    private val prefs: SharedPreferences = context.getSharedPreferences("a401Token", Context.MODE_PRIVATE)
    override suspend fun postIdToken(idToken: String): Flow<String> {

        return flow{
            a401UserApi.postIdToken(idToken, "kakao").let{ response ->
                if(response.isSuccessful) {
                    val accessToken = response.headers()["accessToken"]
                    val refreshToken = response.headers()["refreshToken"]
                    prefs.edit().putString("accessToken", accessToken).apply()
                    prefs.edit().putString("refreshToken", refreshToken).apply()
                    emit("SUCCESS")
                }else {
                    emit("FAIL")
                }
            }
        }
    }

}