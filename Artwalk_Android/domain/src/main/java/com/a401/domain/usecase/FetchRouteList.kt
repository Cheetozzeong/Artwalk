package com.a401.domain.usecase

import com.a401.domain.model.RouteForList
import com.a401.domain.repository.RouteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchRouteList @Inject constructor(
    private val routeRepository: RouteRepository
) {
    suspend operator fun invoke(): Flow<List<RouteForList>> {
        return routeRepository.getRouteForList()
    }
}