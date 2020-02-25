package com.lucers.common.state

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized

/**
 * ForgetPasswordState
 */
data class ForgetPasswordState(
    val phoneNumberError: String? = "",
    val verifyCodeError: String? = "",
    val passwordError: String? = "",
    val confirmPasswordError: String? = "",
    val submitRequest: Async<Any> = Uninitialized,
    val verifyCodeRequest: Async<Any> = Uninitialized
) : MvRxState