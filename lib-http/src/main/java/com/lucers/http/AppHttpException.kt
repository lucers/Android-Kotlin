package com.lucers.http

import com.google.gson.annotations.SerializedName

/**
 * AppHttpException
 */
data class AppHttpException(
    @SerializedName("code")
    val errorCode: Int?,
    @SerializedName(value = "message",alternate = ["msg"])
    var errorMessage: String?
) : Throwable()