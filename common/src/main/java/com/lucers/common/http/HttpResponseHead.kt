package com.lucers.common.http

import com.google.gson.annotations.SerializedName

data class HttpResponseHead(
    @SerializedName("respCode") var responseCode: String? = "",
    @SerializedName("respContent") var responseMessage: String? = ""
)