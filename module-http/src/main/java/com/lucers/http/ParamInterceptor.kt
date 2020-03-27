package com.lucers.http

import com.blankj.utilcode.util.AppUtils
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import okio.Buffer
import java.io.IOException
import java.nio.charset.Charset

/**
 * ParamInterceptor
 *
 * @author Lucers
 */
class ParamInterceptor : Interceptor {

    private val appName = AppUtils.getAppName()

    private val versionCode = AppUtils.getAppVersionCode()

    private val versionName = AppUtils.getAppVersionName()

    private val gson = Gson()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        when (request.method) {
            "POST" -> request = rebuildPostRequest(request)
            "GET" -> request = rebuildGetRequest(request)
        }
        return chain.proceed(request)
    }

    private fun rebuildPostRequest(request: Request): Request {
        val requestBody = request.body
        requestBody?.let {
            when (it) {
                is FormBody -> {
                    val bodyBuilder = FormBody.Builder(Charset.defaultCharset())
                    for (i in 0 until it.size) {
                        bodyBuilder.addEncoded(it.encodedName(i), it.encodedValue(i))
                    }
                    return request.newBuilder()
                        .post(bodyBuilder.build())
                        .build()
                }
                is MultipartBody -> {
                    val bodyBuilder = MultipartBody.Builder()
                    val parts = it.parts
                    for (part in parts) {
                        bodyBuilder.addPart(part)
                    }
                    return request.newBuilder()
                        .post(bodyBuilder.build())
                        .build()
                }
                else -> {
                    var newBody: RequestBody
                    try {
                        var requestParams = if (it.contentLength() == 0L) {
                            JsonObject()
                        } else {
                            val json = getRequestContent(it)
                            gson.fromJson(json, JsonObject::class.java)
                        }
                        val params = request.url.queryParameterNames
                        for (paramName in params) {
                            val parameterValues = request.url.queryParameterValues(paramName)
                            if (parameterValues.isNotEmpty()) {
                                val paramValue = parameterValues[0]
                                requestParams.addProperty(paramName, paramValue)
                            }
                        }
                        requestParams = addCommonParams(requestParams)
                        newBody = requestParams.toString().toRequestBody(it.contentType())
                    } catch (e: Exception) {
                        newBody = it
                        e.printStackTrace()
                    }
                    return request.newBuilder()
                        .post(newBody)
                        .build()
                }
            }
        }
        return request
    }

    private fun addCommonParams(requestParams: JsonObject): JsonObject {
        val params = JsonObject()
        params.add("baseParams", createCommonParams())
        params.add("bizParams", requestParams)
        return params
    }

    private fun createCommonParams(): JsonObject {
        val commonParams = JsonObject()
        commonParams.addProperty("appName", appName)
        return commonParams
    }

    @Throws(IOException::class)
    private fun getRequestContent(requestBody: RequestBody): String {
        val buffer = Buffer()
        requestBody.writeTo(buffer)
        return buffer.readUtf8()
    }

    private fun rebuildGetRequest(request: Request): Request {
        return request.newBuilder()
            .build()
    }
}