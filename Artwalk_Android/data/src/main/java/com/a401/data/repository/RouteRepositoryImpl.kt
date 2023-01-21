package com.a401.data.repository

import com.a401.domain.model.Marker
import com.a401.domain.model.Route
import com.a401.domain.repository.RouteRepository
import javax.inject.Inject

class RouteRepositoryImpl @Inject constructor(): RouteRepository {

    override fun getRouteBetweenTwoMarker(from: Marker, to: Marker, profile: String): Route {
        return Route(
            400.0,
            300.0,
            "{\"coordinates\":[[-74.149234,40.935829],[-74.148426,40.935679],[-74.146917,40.940564],[-74.144981,40.940219],[-74.143088,40.940834],[-74.1424,40.940103],[-74.140894,40.939059],[-74.135422,40.937304],[-74.134798,40.936942],[-74.132992,40.933754],[-74.131264,40.929603],[-74.129913,40.927036],[-74.129683,40.927053],[-74.129563,40.927244],[-74.12926,40.927462]]}"
        )
    }
}
