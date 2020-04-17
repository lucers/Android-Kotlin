package com.lucers.http.interceptor

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.lucers.http.bean.HttpHeader
import com.lucers.http.bean.HttpCommonParam
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
class RequestInterceptor : Interceptor {

    val httpHeader = mutableListOf<HttpHeader>()
    val commonParam = mutableListOf<HttpCommonParam>()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        when (request.method) {
            "POST" -> request = rebuildPostRequest(request)
            "GET" -> request = rebuildGetRequest(request)
        }
        val requestBuilder = request.newBuilder()
        for (httpHeader in httpHeader) {
            if (httpHeader.isEmpty()) {
                continue
            }
            requestBuilder.addHeader(httpHeader.key, httpHeader.value)
        }
        return chain.proceed(requestBuilder.build())
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
                    for (httpParam in commonParam) {
                        if (httpParam.isEmpty()) {
                            continue
                        }
                        bodyBuilder.addEncoded(httpParam.key, httpParam.value)
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
                    for (httpParam in commonParam) {
                        if (httpParam.isEmpty()) {
                            continue
                        }
                        bodyBuilder.addFormDataPart(httpParam.key, httpParam.value)
                    }
                    return request.newBuilder()
                        .post(bodyBuilder.build())
                        .build()
                }
                else -> {
                    var newBody: RequestBody
                    try {
                        val requestParams = if (it.contentLength() == 0L) {
                            JsonObject()
                        } else {
                            val json = getRequestContent(it)
                            Gson().fromJson(json, JsonObject::class.java)
                        }
                        val params = request.url.queryParameterNames
                        for (paramName in params) {
                            val parameterValues = request.url.queryParameterValues(paramName)
                            if (parameterValues.isNotEmpty()) {
                                val paramValue = parameterValues[0]
                                requestParams.addProperty(paramName, paramValue)
                            }
                        }
                        for (httpParam in commonParam) {
                            if (httpParam.isEmpty()) {
                                continue
                            }
                            requestParams.addProperty(httpParam.key, httpParam.value)
                        }
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

    @Throws(IOException::class)
    private fun getRequestContent(requestBody: RequestBody): String {
        val buffer = Buffer()
        requestBody.writeTo(buffer)
        return buffer.readUtf8()
    }

    private fun rebuildGetRequest(request: Request): Request {
        val urlBuilder = request.url.newBuilder()
        for (httpParam in commonParam) {
            if (httpParam.isEmpty()) {
                continue
            }
            urlBuilder.addQueryParameter(httpParam.key, httpParam.value)
        }
        return request.newBuilder().url(urlBuilder.build())
            .build()
    }
}