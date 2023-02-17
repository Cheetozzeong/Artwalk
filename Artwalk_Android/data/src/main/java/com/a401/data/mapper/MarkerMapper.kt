package com.a401.data.mapper

import com.a401.data.model.request.MarkerRequest
import com.a401.domain.model.Marker

fun markerToRequest(data: Marker): MarkerRequest =
    with(data) {
        MarkerRequest(
            longitude,
            latitude
        )
    }
