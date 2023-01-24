package com.a401.domain.usecase

import com.a401.domain.model.Marker
import com.a401.domain.model.Route
import com.a401.domain.repository.RouteRepository
import javax.inject.Inject

class GetRouteForWalkingUseCase @Inject constructor(
    private val routeRepository: RouteRepository
) {

    suspend operator fun invoke(from: Marker, to: Marker): Result<Route> {
        return routeRepository.getRouteForWalking(from, to)
    }

}