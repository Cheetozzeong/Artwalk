package com.a401.data.model.response

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

data class RecordListResponse(
    @SerializedName("records") var records: ArrayList<Records> = arrayListOf(),
    @SerializedName("count") var count: Int?,
    @SerializedName("code") var code: String,
    @SerializedName("message") var message: String
)

data class RecordResponse(
    @SerializedName("code") var code: String,
    @SerializedName("message") var message: String
)

data class DeleteResponse(
    @SerializedName("code") var code: String,
    @SerializedName("count") var count: Int
)

data class EditTitleResponse(
    @SerializedName("title") var title: String
)

data class Records (
    @SerializedName("recordId") var recordId: Int?,

    @SerializedName("duration") var duration: Double?,
    @SerializedName("distance") var distance: Double?,
    @SerializedName("geometry") var geometry: String?,

    @SerializedName("title") var title: String?,
    @SerializedName("creation") var creation: Date?,
    @SerializedName("thumbnail") var thumbnail: String?,
)
