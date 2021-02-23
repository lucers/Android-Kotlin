package com.lucers.http.interceptor

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
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

    val commonParam: HashMap<String, Any> by lazy {
        HashMap()
    }

    val httpHeader: HashMap<String, Any> by lazy {
        HashMap()
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        when (request.method) {
            "POST" -> request = rebuildPostRequest(request)
            "GET" -> request = rebuildGetRequest(request)
        }
        val requestBuilder = request.newBuilder()
        requestBuilder.headers(request.headers)
        httpHeader.keys.forEach {
            if (request.headers[it].isNullOrBlank()) {
                requestBuilder.addHeader(it, httpHeader[it].toString())
            }
        }
        return chain.proceed(requestBuilder.build())
    }

    private fun rebuildPostRequest(request: Request): Request {
        val requestBody = request.body
        requestBody?.let { body ->
            val newBody = when (body) {
                is FormBody -> {
                    val bodyBuilder = FormBody.Builder(Charset.defaultCharset())
                    for (i in 0 until body.size) {
                        bodyBuilder.addEncoded(body.encodedName(i), body.encodedValue(i))
                    }
                    commonParam.keys.forEach {
                        bodyBuilder.addEncoded(it, commonParam[it].toString())
                    }
                    bodyBuilder.build()
                }
                is MultipartBody -> {
                    val bodyBuilder = MultipartBody.Builder()
                    val parts = body.parts
                    for (part in parts) {
                        bodyBuilder.addPart(part)
                    }
                    commonParam.keys.forEach {
                        bodyBuilder.addFormDataPart(it, commonParam[it].toString())
                    }
                    bodyBuilder.build()
                }
                else -> {
                    var newBody: RequestBody
                    try {
                        val requestParams = if (body.contentLength() == 0L) {
                            JsonObject()
                        } else {
                            val json = getRequestContent(body)
                            when {
                                json.startsWith("{") -> {
                                    Gson().fromJson(json, JsonObject::class.java)
                                }
                                json.startsWith("[") -> {
                                    Gson().fromJson(json, JsonArray::class.java)
                                }
                                else -> {
                                    throw JsonParseException("Error Json: $json")
                                }
                            }
                        }

                        if (requestParams is JsonObject) {
                            commonParam.keys.forEach {
                                requestParams.addProperty(it, commonParam[it].toString())
                            }
                        }
                        newBody = requestParams.toString().toRequestBody(body.contentType())
                    } catch (e: Exception) {
                        newBody = body
                        e.printStackTrace()
                    }
                    newBody
                }
            }
            val urlBuilder = request.url.newBuilder()
            commonParam.keys.forEach {
                urlBuilder.addQueryParameter(it, commonParam[it].toString())
            }
            return request.newBuilder()
                .url(urlBuilder.build())
                .post(newBody)
                .build()
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
        commonParam.keys.forEach {
            urlBuilder.addQueryParameter(it, commonParam[it].toString())
        }
        return request.newBuilder().url(urlBuilder.build())
            .build()
    }
}