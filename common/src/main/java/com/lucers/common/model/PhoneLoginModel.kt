package com.lucers.common.model

import com.airbnb.mvrx.*
import com.lucers.common.bean.result.LoginResult
import com.lucers.common.state.PhoneLoginState
import com.lucers.common.utils.CheckUtil
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * PhoneLoginModel
 */
class PhoneLoginModel(initialState: PhoneLoginState) :
    BaseMvRxViewModel<PhoneLoginState>(initialState) {

    private var phoneNumber: String? = null
    private var password: String? = null
    private var qqId: String? = null
    private var wxId: String? = null

    init {
        logStateChanges()
    }

    fun phoneLogin() {
        if (checkPhoneNumber()) return else setState { copy(phoneNumberError = "") }
        if (checkPassword()) return else setState { copy(passwordError = "") }
    }

    private fun checkPhoneNumber(): Boolean {
        return when {
            phoneNumber.isNullOrBlank() -> {
                setState { copy(phoneNumberError = "请输入手机号") }
                true
            }
            phoneNumber?.length != 11 || !CheckUtil.isPhone(phoneNumber ?: "") -> {
                setState { copy(phoneNumberError = "请输入正确的手机号码") }
                true
            }
            else -> false
        }
    }

    private fun checkPassword(): Boolean {
        return when {
            password.isNullOrBlank() -> {
                setState { copy(passwordError = "请输入密码") }
                true
            }
            password?.length ?: 0 < 6 -> {
                setState { copy(passwordError = "密码长度不小于6位数") }
                true
            }
            else -> false
        }
    }

    fun setPhoneNumber(phoneNumber: String?) {
        this.phoneNumber = phoneNumber
        setState {
            copy(phoneNumberError = "")
        }
    }

    fun setPassword(password: String?) {
        this.password = password
        setState {
            copy(passwordError = "")
        }
    }

    fun setQQId(qqId: String?) {
        this.qqId = qqId
        this.wxId = ""
    }

    fun setWxId(wxId: String?) {
        this.wxId = wxId
        this.qqId = ""
    }
}