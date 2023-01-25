package com.a401.data.model.response

import com.google.gson.annotations.SerializedName

data class RouteListResponse(
    @SerializedName("routes") var routes: ArrayList<Routes> = arrayListOf(),
    @SerializedName("code") var code: String,
) {
    data class Routes (
        @SerializedName("duration") var duration: Double,
        @SerializedName("distance") var distance: Double,
        @SerializedName("geometry") var geometry: String
    )
}
