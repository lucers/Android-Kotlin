package com.lucers.http.interceptor

import com.blankj.utilcode.util.AppUtils
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
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