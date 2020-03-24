package com.lucers.common.model

import com.airbnb.mvrx.*
import com.lucers.common.constants.TypeConstants
import com.lucers.common.state.ForgetPasswordState
import com.lucers.common.utils.CheckUtil
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * ForgetPasswordModel
 */
class ForgetPasswordModel(initialState: ForgetPasswordState) :
    BaseMvRxViewModel<ForgetPasswordState>(initialState) {

    private var phoneNumber: String? = null
    private var verifyCode: String? = null
    private var password: String? = null
    private var confirmPassword: String? = null

    init {
        logStateChanges()
    }

    fun submit() {
        if (checkPhoneNumber()) return else setState { copy(phoneNumberError = "") }
        if (checkVerifyCode()) return else setState { copy(verifyCodeError = "") }
        if (checkPassword()) return else setState { copy(passwordError = "") }
        if (checkConfirmPassword()) return else setState { copy(confirmPasswordError = "") }
    }

    fun sendVerifyCode() {
        if (checkPhoneNumber()) return else setState { copy(phoneNumberError = "") }
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

    private fun checkVerifyCode(): Boolean {
        return when {
            verifyCode.isNullOrBlank() -> {
                setState { copy(verifyCodeError = "请输入验证码") }
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

    private fun checkConfirmPassword(): Boolean {
        return when {
            confirmPassword.isNullOrBlank() -> {
                setState { copy(confirmPasswordError = "请输入确认密码") }
                true
            }
            confirmPassword?.length ?: 0 < 6 -> {
                setState { copy(confirmPasswordError = "密码长度不小于6位数") }
                true
            }
            confirmPassword!! != password -> {
                setState { copy(confirmPasswordError = "两次输入密码不同") }
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

    fun setVerifyCode(verifyCode: String?) {
        this.verifyCode = verifyCode
        setState {
            copy(verifyCodeError = "")
        }
    }

    fun setPassword(password: String?) {
        this.password = password
        setState {
            copy(passwordError = "")
        }
    }

    fun setConfirmPassword(confirmPassword: String?) {
        this.confirmPassword = confirmPassword
        setState {
            copy(confirmPasswordError = "")
        }
    }
}