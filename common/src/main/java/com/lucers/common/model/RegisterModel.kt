package com.lucers.common.model

import com.airbnb.mvrx.*
import com.lucers.common.bean.result.RegisterResult
import com.lucers.common.constants.TypeConstants
import com.lucers.common.http.HttpManager
import com.lucers.common.http.HttpResponse
import com.lucers.common.http.service.UserService
import com.lucers.common.state.RegisterState
import com.lucers.common.utils.CheckUtil
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * RegisterModel
 */
class RegisterModel(initialState: RegisterState) :
    BaseMvRxViewModel<RegisterState>(initialState) {

    private var phoneNumber: String? = null
    private var verifyCode: String? = null
    private var accountNumber: String? = null
    private var password: String? = null
    private var confirmPassword: String? = null
    private var qqId: String? = null
    private var wxId: String? = null

    init {
        logStateChanges()
    }

    fun register(loginType: String) {
        if (checkPhoneNumber()) return else setState { copy(phoneNumberError = "") }
        if (checkVerifyCode()) return else setState { copy(verifyCodeError = "") }
        if (checkPassword()) return else setState { copy(passwordError = "") }
        if (checkConfirmPassword()) return else setState { copy(confirmPasswordError = "") }
        val observable = HttpManager.instance
            .createService(UserService::class.java)
            .register(
                phoneNumber!!, password!!, verifyCode!!, loginType, qqId, wxId, accountNumber
            )
        HttpManager.instance
            .subscribe(observable, object : Observer<HttpResponse<RegisterResult>> {
                override fun onComplete() {
                    setState { copy(registerRequest = Uninitialized) }
                }

                override fun onSubscribe(d: Disposable) {
                    setState { copy(registerRequest = Loading()) }
                }

                override fun onNext(t: HttpResponse<RegisterResult>) {
                    t.results?.let {
                        setState { copy(registerRequest = Success(it)) }
                    }
                }

                override fun onError(e: Throwable) {
                    setState { copy(registerRequest = Fail(e)) }
                    setState { copy(registerRequest = Uninitialized) }
                }
            })
    }

    fun sendVerifyCode(loginType: String) {
        if (checkPhoneNumber()) return else setState { copy(phoneNumberError = "") }
        val observable = HttpManager.instance
            .createService(UserService::class.java)
            .sendVerifyCode(phoneNumber!!, TypeConstants.verifyCodeTypeRegister, loginType)
        HttpManager.instance
            .subscribe(observable, object : Observer<HttpResponse<Any>> {
                override fun onComplete() {
                    setState { copy(verifyCodeRequest = Uninitialized) }
                }

                override fun onSubscribe(d: Disposable) {
                    setState { copy(verifyCodeRequest = Loading()) }
                }

                override fun onNext(t: HttpResponse<Any>) {
                    setState { copy(verifyCodeRequest = Success(t)) }
                }

                override fun onError(e: Throwable) {
                    setState { copy(verifyCodeRequest = Fail(e)) }
                    setState { copy(verifyCodeRequest = Uninitialized) }
                }
            })
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

    fun setThirdLoginId(thirdLoginType: String?, thirdLoginId: String?) {
        when (thirdLoginType) {
            TypeConstants.loginTypeQQ -> qqId = thirdLoginId
            TypeConstants.loginTypeWeChat -> wxId = thirdLoginId
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

    fun setAccountNumber(accountNumber: String?) {
        this.accountNumber = accountNumber
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