package com.lucers.common.bean

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("id")
    val userId: String,
    val isCertified: String
)