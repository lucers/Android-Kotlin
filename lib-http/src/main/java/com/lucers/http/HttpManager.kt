package com.lucers.http

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownServiceException
import java.util.concurrent.TimeUnit

/**
 * HttpManager
 */
class HttpManager private constructor() {

    private var retrofit: Retrofit

    private var httpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private object HttpManagerHolder {
        val holder = HttpManager()
    }

    companion object {
        val instance = HttpManagerHolder.holder
    }

    init {
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClient = httpClient.newBuilder()
                .addInterceptor(httpLoggingInterceptor)
                .build()
        }

        retrofit = Retrofit.Builder()
            .client(httpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    fun <T> subscribe(observable: Observable<HttpResponse<T>>, observer: Observer<HttpResponse<T>>) {
        compose(observable)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    private fun <T> compose(observable: Observable<HttpResponse<T>>): Observable<HttpResponse<T>> {
        return observable
            .onErrorReturn { throwable ->
                val httpResponse: HttpResponse<T> = HttpResponse()
                when (throwable) {
                    is UnknownServiceException -> {
                        httpResponse.responseCode = HttpConstants.unknownServiceException
                    }
                    is ConnectException -> {
                        httpResponse.responseCode = HttpConstants.connectException
                    }
                    is SocketTimeoutException -> {
                        httpResponse.responseCode = HttpConstants.socketTimeoutException
                    }
                    is SocketException -> {
                        httpResponse.responseCode = HttpConstants.socketException
                    }
                    is HttpException -> {
                        httpResponse.responseCode = throwable.code()
                    }
                    else -> {
                        httpResponse.responseCode = HttpConstants.unknownException
                    }
                }
                httpResponse.responseMessage = "网络请求发生错误"
                httpResponse
            }
            .map { response ->
                when (response.responseCode) {
                    HttpConstants.success -> {
                        response
                    }
                    else -> {
                        throw RuntimeException(response.responseMessage + ":" + response.responseCode)
                    }
                }
            }
    }

    @Suppress("UNCHECKED_CAST")
    fun <H> createService(serviceClass: Class<H>, retrofit: Retrofit = this.retrofit): H {
        return retrofit.create(serviceClass) as H
    }

    fun newClient(httpClient: OkHttpClient) {
        this.httpClient = httpClient
        retrofit = retrofit.newBuilder()
            .client(httpClient)
            .build()
    }

    fun rebuildClient(): OkHttpClient.Builder {
        return httpClient.newBuilder()
    }

    fun newRetrofit(retrofit: Retrofit) {
        this.retrofit = retrofit
    }

    fun rebuildRetrofit(): Retrofit.Builder {
        return retrofit.newBuilder()
    }
}