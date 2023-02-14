package com.a401.data.model.response

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

data class RecordListResponse(
    @SerializedName("records") var records: ArrayList<Records> = arrayListOf(),
    @SerializedName("count") var count: Int?,
    @SerializedName("code") var code: String,
)

data class Records (
    @SerializedName("recordId") var recordId: Int?,

    @SerializedName("duration") var duration: Int?,
    @SerializedName("distance") var distance: Double?,
    @SerializedName("geometry") var geometry: String?,

    @SerializedName("detail") var detail: String?,
    @SerializedName("creation") var creation: Date?,
    @SerializedName("thumbnail") var thumbnail: String?,
)
