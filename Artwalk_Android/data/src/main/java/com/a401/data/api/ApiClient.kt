package com.a401.data.api

import com.a401.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object ApiClient {

    fun getMapboxDirectionsApiService(): MapboxDirectionsApiService = getInstance(BuildConfig.MAPBOX_DIRECTIONS_URL).create(MapboxDirectionsApiService::class.java)
//    fun getRouteServerApiService(): RouteServerApiService = getInstance("").create(RouteServerApiService::class.java)

    private fun getInstance(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(provideOkHttpClient(AppInterceptor()))
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    private fun provideOkHttpClient(interceptor: AppInterceptor): OkHttpClient = OkHttpClient.Builder().run {
        addInterceptor(interceptor)
        build()
    }

    class AppInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain) : Response = with(chain) {
            val newRequest = request().newBuilder()
                .addHeader("Accept", "application/json")
                .build()
            proceed(newRequest)
        }
    }
}