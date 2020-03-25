package com.lucers.common.service

import com.lucers.http.HttpResponse
import com.lucers.common.bean.result.LoginResult
import com.lucers.common.bean.result.RegisterResult
import com.lucers.common.bean.result.ThirdLoginResult
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * UserService
 */
interface UserService {

    @FormUrlEncoded
    @POST("api/user/baseApi/loginTwo")
    fun phoneLogin(
        @Field("username") phoneNumber: String,
        @Field("password") password: String,
        @Field("type") type: String?,
        @Field("clientId") clientId: String? = null,
        @Field("qqId") qqId: String? = null,
        @Field("wxId") wxId: String? = null
    ): Observable<HttpResponse<LoginResult>>

    @FormUrlEncoded
    @POST("api/user/baseApi/selectAuthorize")
    fun thirdLoginAuthor(
        @Field("openId") authorCode: String,
        @Field("userType") userType: String,
        @Field("type") loginType: String
    ): Observable<HttpResponse<ThirdLoginResult>>

    @FormUrlEncoded
    @POST("api/user/baseApi/sendCode")
    fun sendVerifyCode(
        @Field("phone") phoneNumber: String,
        @Field("type") loginType: String,
        @Field("phoneType") userType: String
    ): Observable<HttpResponse<Any>>

    @FormUrlEncoded
    @POST("api/user/baseApi/registered")
    fun register(
        @Field("username") phoneNumber: String,
        @Field("password") password: String,
        @Field("code") verifyCode: String,
        @Field("type") type: String,
        @Field("qqId") qqId: String? = null,
        @Field("wxId") wxId: String? = null,
        @Field("accountNumbers") accountNumbers: String? = null
    ): Observable<HttpResponse<RegisterResult>>

    @FormUrlEncoded
    @POST("api/user/baseApi/forgetPwd")
    fun forgetPassword(
        @Field("username") phoneNumber: String,
        @Field("code") verifyCode: String,
        @Field("newPwd1") password: String,
        @Field("newPwd2") confirmPassword: String,
        @Field("type") loginType: String
    ): Observable<HttpResponse<Any>>
}