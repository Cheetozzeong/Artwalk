package com.a401.domain.usecase

import com.a401.domain.model.RouteForDraw
import com.a401.domain.repository.RouteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRouteUseCase @Inject constructor(
    private val routeRepository: RouteRepository,
) {
    suspend operator fun invoke(routeForDraw: RouteForDraw): Flow<String> {
        return routeRepository.postRoute(routeForDraw)
    }

}