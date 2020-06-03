package com.lucers.http

import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * HttpApi
 */
interface HttpApi {

    @GET
    fun getRequest(@Url url: String): Observable<String>

    @GET("/")
    fun getRequest(): Observable<String>

    @Streaming
    @GET("/test.text")
    fun getDownload(): Observable<ResponseBody>

    @Streaming
    @GET
    fun getDownload(@Url url: String): Observable<ResponseBody>

    @POST
    fun postRequest(@Url url: String): Observable<String>

    @POST("/")
    fun postRequest(): Observable<String>

    @Streaming
    @POST
    fun postDownload(): Observable<ResponseBody>

    @Streaming
    @POST
    fun postDownload(@Url url: String): Observable<ResponseBody>
}