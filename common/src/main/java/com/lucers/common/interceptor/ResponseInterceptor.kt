package com.lucers.common.interceptor

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.lucers.common.utils.AppUtil
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.internal.EverythingIsNonNull
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.Charset

/**
 * ResponseInterceptor
 *
 * @author Lucers
 * @date 2019/9/15 0015
 */
class ResponseInterceptor constructor(context: Context) : Interceptor {

    internal val appName: String = AppUtil.getAppName(context)

    internal val versionCode: Long = AppUtil.getVersionCode(context)

    internal val versionName: String = AppUtil.getVersionName(context)

    @EverythingIsNonNull
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        var responseBody = response.body
        responseBody?.let {
            val mediaType = it.contentType()
            val bufferedReader = BufferedReader(
                InputStreamReader(
                    it.byteStream(),
                    Charset.defaultCharset()
                )
            )
            val result = bufferedReader.readLine()
            val jsonObject = Gson().fromJson(result, JsonObject::class.java)
            responseBody = Gson().toJson(jsonObject).toResponseBody(mediaType)
        }
        val builder = response.newBuilder()
        responseBody?.let {
            builder.body(it)
        }
        return builder.build()
    }
}