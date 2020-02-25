package com.lucers.common.bean.result

import com.google.gson.annotations.SerializedName
import com.lucers.common.bean.UserInfo

data class RegisterResult(
    @SerializedName("sysUser")
    val userInfo: UserInfo?
)