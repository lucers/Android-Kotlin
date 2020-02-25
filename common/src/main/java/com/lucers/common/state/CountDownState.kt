package com.lucers.common.state

import com.airbnb.mvrx.MvRxState

/**
 * CountDownState
 */
data class CountDownState(
    val countTime: Long,
    val countOver: Boolean = true
) : MvRxState