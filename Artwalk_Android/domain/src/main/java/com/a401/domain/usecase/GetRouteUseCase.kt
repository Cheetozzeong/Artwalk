package com.a401.domain.usecase

import com.a401.domain.model.Marker
import com.a401.domain.model.RouteForDraw
import com.a401.domain.repository.RouteRepository
import javax.inject.Inject

class GetRouteForWalkingUseCase @Inject constructor(
    private val routeRepository: RouteRepository
) {

    suspend operator fun invoke(from: Marker, to: Marker): Result<RouteForDraw> {
        return routeRepository.getRouteForWalking(from, to)
    }

}