package com.a401.data.model.request

data class MarkerRequest(
    var longitude: Double,
    var latitude: Double,
) {
    override fun toString(): String {
        return "$longitude,$latitude;"
    }
}