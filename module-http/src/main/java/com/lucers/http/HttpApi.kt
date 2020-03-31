package com.lucers.http

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

/**
 * HttpApi
 */
interface HttpApi {

    @GET("/")
    fun getRequest(@Url url: String) : Observable<Any>

    @GET("/")
    fun getRequest() : Observable<Any>

    @POST("/")
    fun postRequest(@Url url: String) : Observable<Any>

    @POST("/")
    fun postRequest() : Observable<Any>
}