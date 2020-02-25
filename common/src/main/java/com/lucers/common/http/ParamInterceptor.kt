package com.lucers.common.http

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.lucers.common.constants.MMKVConstants
import com.lucers.common.utils.LogUtil
import com.tencent.mmkv.MMKV
import okhttp3.*
import okhttp3.internal.annotations.EverythingIsNonNull
import okio.Buffer
import java.io.IOException
import java.net.URLDecoder
import java.nio.charset.Charset

/**
 * ParamInterceptor
 *
 * @author Lucers
 */
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ParamInterceptor : Interceptor {

    private val tag = "ParamInterceptor"
    private var userToken: String? = ""

    @EverythingIsNonNull
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var requestUrl = request.url().toString()
        LogUtil.d(tag, "requestUrl:$requestUrl")
        userToken = MMKV.defaultMMKV().decodeString(MMKVConstants.userToken)
        LogUtil.d(tag, "userToken:$userToken")
        when (request.method()) {
            "POST" -> request = rebuildPostRequest(request)
            "GET" -> request = rebuildGetRequest(request)
        }
        requestUrl = request.url().toString()
        LogUtil.d(tag, "ResultUrl:$requestUrl")
        return chain.proceed(request)
    }

    @EverythingIsNonNull
    private fun rebuildPostRequest(request: Request): Request {
        val requestBody = request.body()
        requestBody?.let {
            if (it is FormBody) {
                val bodyBuilder = FormBody.Builder(Charset.defaultCharset())
                LogUtil.d(tag, "FormBody:" + Gson().toJson(it))
                for (i in 0 until it.size()) {
                    bodyBuilder.addEncoded(it.encodedName(i), it.encodedValue(i))
                    LogUtil.d(
                        tag, "name:" + it.encodedName(i) + "==" +
                                URLDecoder.decode(it.encodedValue(i), "UTF-8")
                    )
                }
                return request.newBuilder()
                    .post(bodyBuilder.build())
                    .addHeader("token", userToken ?: "")
                    .build()
            }
            if (it is MultipartBody) {
                val bodyBuilder = MultipartBody.Builder()
                LogUtil.d(tag, "MultipartBody:" + Gson().toJson(it))
                val parts = it.parts()
                for (part in parts) {
                    bodyBuilder.addPart(part)
                }
                return request.newBuilder()
                    .post(bodyBuilder.build())
                    .addHeader("token", userToken ?: "")
                    .build()
            }
            var newBody: RequestBody
            try {
                val requestParams: JsonObject
                requestParams = if (it.contentLength() == 0L) {
                    JsonObject()
                } else {
                    val json = getRequestContent(it)
                    LogUtil.d(tag, "RequestBody:$json")
                    Gson().fromJson(json, JsonObject::class.java)
                }
                val params = request.url().queryParameterNames()
                for (paramName in params) {
                    val parameterValues =
                        request.url().queryParameterValues(paramName)
                    if (parameterValues.size > 0) {
                        val paramValue = parameterValues[0]
                        requestParams.addProperty(paramName, paramValue)
                    }
                }
                LogUtil.d(tag, "paramBody:" + Gson().toJson(requestParams))
                newBody = RequestBody.create(it.contentType(), requestParams.toString())
            } catch (e: Exception) {
                newBody = it
                e.printStackTrace()
            }
            return request.newBuilder()
                .post(newBody)
                .addHeader("token", userToken ?: "")
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

    @EverythingIsNonNull
    private fun rebuildGetRequest(request: Request): Request {
        return request.newBuilder()
            .addHeader("token", userToken ?: "")
            .url(
                request.url()
                    .newBuilder()
                    .build()
            )
            .build()
    }
}