package com.lucers.common.bean.result

import com.google.gson.annotations.SerializedName

/**
 * AppVersionResult
 */
data class AppVersionResult(
    val versionCode: String = "",
    @SerializedName("version")
    val versionName: String = "",
    @SerializedName("download")
    val updateUrl: String = "",
    @SerializedName("message")
    val updateContent: String = ""
)