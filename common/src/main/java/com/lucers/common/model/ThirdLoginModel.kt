package com.lucers.common.model

import com.airbnb.mvrx.*
import com.lucers.common.bean.result.ThirdLoginResult
import com.lucers.common.constants.TypeConstants
import com.lucers.common.state.ThirdLoginState
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * ThirdLoginModel
 */
class ThirdLoginModel(initialState: ThirdLoginState) :
    BaseMvRxViewModel<ThirdLoginState>(initialState) {

    init {
        logStateChanges()
    }

    fun weChatLogin(weChatAuthorCode: String?) {
        weChatAuthorCode?.let {
            thirdLogin()
        }
    }

    fun qqLogin(openId: String?) {
        openId?.let {
            thirdLogin()
        }
    }

    private fun thirdLogin() {
    }
}