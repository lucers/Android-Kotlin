package com.lucers.common.state

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.lucers.common.bean.result.LoginResult

/**
 * PhoneLoginState
 */
data class PhoneLoginState(
    val phoneNumberError: String? = "",
    val passwordError: String? = "",
    val request: Async<LoginResult> = Uninitialized
) : MvRxState