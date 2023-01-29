package com.a401.data.api

import com.a401.data.model.response.RouteListResponse

interface RouteServerApiService {

    suspend fun getRouteList(): RouteListResponse

}