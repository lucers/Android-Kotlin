package com.lucers.common.http

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.lucers.common.utils.LogUtil
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.internal.annotations.EverythingIsNonNull
import okhttp3.internal.http.HttpHeaders
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
class ResponseInterceptor : Interceptor {

    private val tag = "ResponseInterceptor"

    @EverythingIsNonNull
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        var responseBody = response.body()
        if (HttpHeaders.hasBody(response) && responseBody != null) {
            val mediaType = responseBody.contentType()
            val bufferedReader = BufferedReader(
                InputStreamReader(
                    responseBody.byteStream(),
                    Charset.defaultCharset()
                )
            )
            val result = bufferedReader.readLine()
            LogUtil.d(tag, "response:$result")
            val jsonObject = Gson().fromJson(result, JsonObject::class.java)
            responseBody = ResponseBody.create(mediaType, Gson().toJson(jsonObject))
        }
        val builder = response.newBuilder()
        responseBody?.let {
            builder.body(responseBody)
        }
        return builder.build()
    }
}