package com.a401.data.model.response

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

data class RouteListResponse(
    // TODO: Back에서 data -> routes로 변경하면 수정할 것
    @SerializedName("data") var routes: ArrayList<Routes> = arrayListOf(),
//    @SerializedName("routes") var routes: ArrayList<Routes> = arrayListOf(),
    @SerializedName("code") var code: String,
)

data class Routes (
    @SerializedName("routeId") var routeId: Int?,

    @SerializedName("duration") var duration: Double?,
    @SerializedName("distance") var distance: Double?,
    @SerializedName("geometry") var geometry: String?,

    @SerializedName("title") var title: String?,
    @SerializedName("creation") var creation: Date?,
    @SerializedName("thumbnail") var thumbnail: String?,
)