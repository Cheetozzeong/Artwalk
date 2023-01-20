package com.a401.domain.repository

import com.a401.domain.model.Marker
import com.a401.domain.model.Route

interface RouteRepository {
    fun getRouteBetweenTwoMarker(from: Marker, to: Marker, profile: String): Route
}