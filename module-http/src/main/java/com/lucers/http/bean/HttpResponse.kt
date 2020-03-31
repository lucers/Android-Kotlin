package com.lucers.http.bean

import com.google.gson.annotations.SerializedName

data class HttpResponse<T>(
    @SerializedName("msg")
    var responseMessage: String? = null,
    @SerializedName("errorCode")
    var responseCode: Int? = -1,
    @SerializedName("data")
    var data: T? = null
)