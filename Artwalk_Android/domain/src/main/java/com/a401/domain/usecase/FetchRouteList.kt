package com.a401.domain.usecase

import com.a401.domain.model.RouteForList
import com.a401.domain.repository.RouteRepository
import javax.inject.Inject

class FetchRouteList @Inject constructor(
    private val routeRepository: RouteRepository
) {
    suspend fun invoke(): List<RouteForList> {
        return routeRepository.getRoutForList()
    }
}