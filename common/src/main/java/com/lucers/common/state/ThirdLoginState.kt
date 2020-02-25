package com.lucers.common.state

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.lucers.common.bean.result.ThirdLoginResult
import com.lucers.common.constants.TypeConstants

/**
 * thirdLoginState
 */
data class ThirdLoginState(
    val thirdLoginType: String? = TypeConstants.loginTypeQQ,
    val request: Async<ThirdLoginResult> = Uninitialized
) : MvRxState