package com.a401.domain.repository

import com.a401.domain.model.Marker
import com.a401.domain.model.Route

interface RouteRepository {
    suspend fun getRouteForWalking(from: Marker, to: Marker): Result<Route>
}