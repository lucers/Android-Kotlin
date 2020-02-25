package com.lucers.common.model

import com.airbnb.mvrx.*
import com.lucers.common.bean.result.ThirdLoginResult
import com.lucers.common.constants.TypeConstants
import com.lucers.common.http.HttpManager
import com.lucers.common.http.HttpResponse
import com.lucers.common.http.service.UserService
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

    fun weChatLogin(weChatAuthorCode: String?, userType: String) {
        weChatAuthorCode?.let {
            thirdLogin(it, TypeConstants.loginTypeWeChat, userType)
        }
    }

    fun qqLogin(openId: String?, userType: String) {
        openId?.let {
            thirdLogin(it, TypeConstants.loginTypeQQ, userType)
        }
    }

    private fun thirdLogin(openId: String, loginType: String, userType: String) {
        val observable = HttpManager.instance
            .createService(UserService::class.java)
            .thirdLoginAuthor(openId, userType, loginType)
        HttpManager.instance.subscribe(
            observable,
            object : Observer<HttpResponse<ThirdLoginResult>> {
                override fun onComplete() {
                    setState { copy(request = Uninitialized) }
                }

                override fun onSubscribe(d: Disposable) {
                    setState { copy(request = Loading()) }
                }

                override fun onNext(t: HttpResponse<ThirdLoginResult>) {
                    t.results?.let {
                        setState { copy(request = Success(it), thirdLoginType = loginType) }
                    }
                }

                override fun onError(e: Throwable) {
                    setState { copy(request = Fail(e)) }
                    setState { copy(request = Uninitialized) }
                }
            })
    }
}