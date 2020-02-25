package com.lucers.common.bean.result

import com.google.gson.annotations.SerializedName
import com.lucers.common.bean.UserInfo

data class LoginResult(
    @SerializedName("info")
    val userInfo: UserInfo?,
    val token: String
)