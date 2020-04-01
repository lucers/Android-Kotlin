package com.lucers.http

import com.lucers.common.BuildConfig
import com.lucers.http.interceptor.RequestInterceptor
import com.lucers.http.interceptor.ResponseInterceptor
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

    var retrofit: Retrofit? = null

    init {
        val builder = OkHttpClient.Builder()
            .addInterceptor(RequestInterceptor())
            .addInterceptor(ResponseInterceptor())

        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(httpLoggingInterceptor)
                .build()
        }
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(builder.build())
            .build()
    }

    @Suppress("UNCHECKED_CAST")
    fun <H> createApi(serviceClass: Class<H>): H {
        return retrofit?.create(serviceClass) as H
    }

    fun updateBaseUrl(url: String?) {
        try {
            this.retrofit = this.retrofit?.newBuilder()
                ?.baseUrl(url!!)
                ?.build()
        } catch (e: Exception) {
            throw e
        }
    }

    fun updateBaseUrl(url: URL?) {
        try {
            this.retrofit = this.retrofit?.newBuilder()
                ?.baseUrl(url!!)
                ?.build()
        } catch (e: Exception) {
            throw e
        }
    }

    fun updateBaseUrl(url: HttpUrl?) {
        try {
            this.retrofit = this.retrofit?.newBuilder()
                ?.baseUrl(url!!)
                ?.build()
        } catch (e: Exception) {
            throw e
        }
    }
}