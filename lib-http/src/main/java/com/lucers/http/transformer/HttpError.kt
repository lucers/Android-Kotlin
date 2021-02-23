package com.lucers.http.transformer

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.google.gson.JsonSyntaxException
import com.lucers.http.AppHttpException
import com.lucers.http.HttpStatusConstants
import com.lucers.http.R
import com.lucers.http.bean.HttpResponse
import io.reactivex.rxjava3.core.ObservableTransformer
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownServiceException
import javax.net.ssl.SSLHandshakeException

/**
 * HttpError
 */
object HttpError {

    fun <T> onError(): ObservableTransformer<HttpResponse<T>, HttpResponse<T>> {
        return ObservableTransformer {
            // http status onError
            it.onErrorReturn { throwable ->
                LogUtils.e(throwable)
                val httpResponse: HttpResponse<T> = HttpResponse(data = null)
                httpResponse.success = false
                when (throwable) {
                    is UnknownServiceException -> {
                        httpResponse.responseCode = HttpStatusConstants.unknownServiceException
                        httpResponse.responseMessage = StringUtils.getString(R.string.un_know_service_exception)
                    }
                    is ConnectException -> {
                        httpResponse.responseCode = HttpStatusConstants.connectException
                        httpResponse.responseMessage = StringUtils.getString(R.string.connect_exception)
                    }
                    is SocketTimeoutException -> {
                        httpResponse.responseCode = HttpStatusConstants.socketTimeoutException
                        httpResponse.responseMessage = StringUtils.getString(R.string.socket_timeout_exception)
                    }
                    is SocketException -> {
                        httpResponse.responseCode = HttpStatusConstants.socketException
                        httpResponse.responseMessage = StringUtils.getString(R.string.socket_exception)
                    }
                    is JsonSyntaxException -> {
                        httpResponse.responseCode = HttpStatusConstants.jsonSyntaxException
                        httpResponse.responseMessage = StringUtils.getString(R.string.json_syntax_exception)
                    }
                    is SSLHandshakeException -> {
                        httpResponse.responseCode = HttpStatusConstants.sslHandshakeException
                        httpResponse.responseMessage = StringUtils.getString(R.string.ssl_hand_shake_exception)
                    }
                    is HttpException -> {
                        httpResponse.responseCode = throwable.code()
                        httpResponse.responseMessage = StringUtils.getString(R.string.http_exception)
                    }
                    else -> {
                        httpResponse.responseCode = HttpStatusConstants.unknownException
                        httpResponse.responseMessage = StringUtils.getString(R.string.un_know_exception)
                    }
                }
                httpResponse
            }
                .map { response ->
                    if (response.success) {
                        response
                    } else {
                        throw AppHttpException(response.responseCode, response.responseMessage)
                    }
                }
        }
    }
}