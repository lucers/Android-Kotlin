package com.lucers.common.http

import com.google.gson.annotations.SerializedName

data class HttpResponse<T>(
    @SerializedName("head") var head: HttpResponseHead,
    @SerializedName("body") var results: T?
)