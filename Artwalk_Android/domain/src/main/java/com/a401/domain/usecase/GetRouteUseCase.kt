package com.a401.domain.usecase

import com.a401.domain.model.Marker
import com.a401.domain.model.Route
import com.a401.domain.repository.FakeRouteRepository
import com.a401.domain.repository.RouteRepository

class GetRouteUseCase (
//    private val routeRepository: RouteRepository,
    private val fakeRepository: FakeRouteRepository
) {

    operator fun invoke(from: Marker, to: Marker, profile: String): Route {
        return fakeRepository.getRouteBetweenTwoMarker(from, to, profile)
    }

}