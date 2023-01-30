package com.a401.domain.repository

import com.a401.domain.model.Marker
import com.a401.domain.model.RouteForDraw
import com.a401.domain.model.RouteForList
import kotlinx.coroutines.flow.Flow

interface RouteRepository {
    suspend fun getRouteForWalking(from: Marker, to: Marker): Result<RouteForDraw>

    suspend fun getRoutForList(): Flow<List<RouteForList>>
}