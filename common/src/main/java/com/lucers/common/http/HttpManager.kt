package com.lucers.common.http

import com.google.gson.Gson
import com.lucers.common.constants.HttpConstants
import com.lucers.common.utils.LogUtil
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

/**
 * HttpManager
 */
class HttpManager private constructor() {

    private val tag = "HttpManager"

    private var baseUrl = "http://192.168.3.43:8888/"

    private var retrofit: Retrofit? = null

    private object HttpManagerHolder {
        val holder = HttpManager()
    }

    companion object {
        val instance = HttpManagerHolder.holder
    }

    init {
        val httpClient = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(ParamInterceptor())
            .addInterceptor(ResponseInterceptor())
            .build()

        retrofit = Retrofit.Builder()
            .client(httpClient)
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Suppress("UNCHECKED_CAST")
    fun <H> createService(serviceClass: Class<H>): H {
        return retrofit?.newBuilder()
            ?.baseUrl(baseUrl)
            ?.build()
            ?.create(serviceClass) as H
    }

    fun <T> subscribe(
        observable: Observable<HttpResponse<T>>,
        observer: Observer<HttpResponse<T>>
    ) {
        compose(observable)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    private fun <T> compose(observable: Observable<HttpResponse<T>>): Observable<HttpResponse<T>> {
        return observable.onErrorReturn { throwable ->
            val httpResponse: HttpResponse<T> = HttpResponse(HttpResponseHead(), null)
            when (throwable) {
                is HttpException -> {
                    LogUtil.e(tag, "HttpException:" + Gson().toJson(throwable))
                    httpResponse.head.responseCode = throwable.code().toString()
                }
                is SocketTimeoutException -> {
                    LogUtil.e(tag, "SocketTimeoutException:" + Gson().toJson(throwable))
                    httpResponse.head.responseCode = "408"
                }
                is ConnectException -> {
                    LogUtil.e(tag, "ConnectException:" + Gson().toJson(throwable))
                    httpResponse.head.responseCode = "404"
                }
                is SocketException -> {
                    LogUtil.e(tag, "SocketException:" + Gson().toJson(throwable))
                    httpResponse.head.responseCode = "413"
                }
                else -> {
                    httpResponse.head.responseCode = "999999"
                    LogUtil.e(tag, "throwable:$throwable")
                }
            }
            httpResponse.head.responseMessage = "网络连接错误"
            httpResponse
        }.map { response ->
            when (response.head.responseCode) {
                HttpConstants.success -> {
                    response
                }
                else -> {
                    throw RuntimeException(response.head.responseMessage)
                }
            }
        }
    }
}