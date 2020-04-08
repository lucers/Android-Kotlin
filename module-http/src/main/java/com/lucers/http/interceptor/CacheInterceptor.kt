package com.lucers.http.interceptor

import com.blankj.utilcode.util.AppUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * CacheInterceptor
 *
 * @author Lucers
 * @date 2019/9/15 0015
 */
class CacheInterceptor : Interceptor {

    private val appName = AppUtils.getAppName()

    private val versionCode = AppUtils.getAppVersionCode()

    private val versionName = AppUtils.getAppVersionName()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }
}