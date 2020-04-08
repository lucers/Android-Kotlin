package com.lucers.http

import com.lucers.common.BuildConfig
import com.lucers.http.bean.HttpHeader
import com.lucers.http.bean.HttpParam
import com.lucers.http.interceptor.CacheInterceptor
import com.lucers.http.interceptor.RequestInterceptor
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.net.URL

/**
 * HttpManager
 */
object HttpManager {

    private const val BASE_URL = BuildConfig.BASE_URL

    private val retrofitBuilder = Retrofit.Builder()

    private val requestInterceptor = RequestInterceptor()

    private val cacheInterceptor = CacheInterceptor()

    init {
        val clientBuilder = OkHttpClient.Builder()
            .addInterceptor(cacheInterceptor)
            .addInterceptor(requestInterceptor)

        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(httpLoggingInterceptor)
        }
        retrofitBuilder.baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientBuilder.build())
            .build()
    }

    @Suppress("UNCHECKED_CAST")
    fun <H> createApi(serviceClass: Class<H>): H {
        return retrofitBuilder.build().create(serviceClass) as H
    }

    fun updateBaseUrl(url: String) {
        retrofitBuilder.baseUrl(url)
    }

    fun updateBaseUrl(url: URL) {
        retrofitBuilder.baseUrl(url)
    }

    fun updateBaseUrl(url: HttpUrl) {
        retrofitBuilder.baseUrl(url)
    }

    fun getBaseUrl(): HttpUrl = retrofitBuilder.build().baseUrl()

    fun setCommonParam(commonParam: MutableList<HttpParam>) {
        requestInterceptor.commonParam.clear()
        requestInterceptor.commonParam.addAll(commonParam)
    }

    fun setHttpHeader(httpHeader: MutableList<HttpHeader>) {
        requestInterceptor.httpHeader.clear()
        requestInterceptor.httpHeader.addAll(httpHeader)
    }

    fun getHttpHeader(): MutableList<HttpHeader> {
        return requestInterceptor.httpHeader
    }

    fun getCommonParam(): MutableList<HttpParam> {
        return requestInterceptor.commonParam
    }
}