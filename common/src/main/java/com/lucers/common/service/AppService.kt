package com.lucers.common.service

import com.lucers.http.HttpResponse
import com.lucers.common.bean.result.AppVersionResult
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * AppService
 */
interface AppService {

    @POST("app/version/checkUpdate")
    fun getAppVersion(): Observable<HttpResponse<AppVersionResult>>

    @Streaming
    @GET
    fun downloadFile(@Url fileUrl: String): Observable<ResponseBody>
}