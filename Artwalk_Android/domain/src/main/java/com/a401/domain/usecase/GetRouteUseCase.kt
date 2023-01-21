package com.a401.domain.usecase

import com.a401.domain.model.Marker
import com.a401.domain.model.Route
import com.a401.domain.repository.RouteRepository
import javax.inject.Inject

class GetRouteUseCase @Inject constructor(
    private val routeRepository: RouteRepository
) {

    operator fun invoke(from: Marker, to: Marker): Route {
        return routeRepository.getRoute(from, to)
    }

}