package com.lucers.http.transformer

import com.lucers.http.HttpConstants
import com.lucers.http.bean.HttpResponse
import io.reactivex.ObservableTransformer
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownServiceException

/**
 * HttpError
 */
object HttpError {

    fun <T> onError(): ObservableTransformer<T, HttpResponse<T>> {
        return ObservableTransformer {
            it.map { data ->
                val httpResponse: HttpResponse<T> = HttpResponse()
                httpResponse.data = data
                httpResponse
            }
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
                    httpResponse.responseMessage = throwable.javaClass.name
                    httpResponse
                }
                .map { response ->
                    when (response.responseCode) {
                        HttpConstants.success -> {
                            response
                        }
                        else -> {
                            throw RuntimeException(response.responseMessage)
                        }
                    }
                }
        }
    }
}