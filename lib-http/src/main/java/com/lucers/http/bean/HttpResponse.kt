package com.lucers.http.bean

import com.google.gson.annotations.SerializedName

data class HttpResponse<T>(

    @SerializedName("message")
    var responseMessage: String? = null,

    @SerializedName("code")
    var responseCode: Int? = -1,

    @SerializedName("data")
    val data: T?,

    @SerializedName("success")
    var success: Boolean = false
)