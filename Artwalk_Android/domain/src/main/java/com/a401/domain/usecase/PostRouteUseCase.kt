package com.a401.domain.usecase

import com.a401.domain.model.RouteForDraw
import com.a401.domain.repository.RouteRepository
import javax.inject.Inject

class PostRouteUseCase @Inject constructor(
    private val routeRepository: RouteRepository,
) {
    suspend operator fun invoke(routeForDraw: RouteForDraw) {
        return routeRepository.postRoute(routeForDraw)
    }

}