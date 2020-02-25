package com.lucers.common.state

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.lucers.common.bean.result.RegisterResult

/**
 * RegisterState
 */
data class RegisterState(
    val phoneNumberError: String? = "",
    val verifyCodeError: String? = "",
    val passwordError: String? = "",
    val confirmPasswordError: String? = "",
    val registerRequest: Async<RegisterResult> = Uninitialized,
    val verifyCodeRequest: Async<Any> = Uninitialized
) : MvRxState