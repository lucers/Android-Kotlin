package com.lucers.http

/**
 * BundleConstants
 */
object HttpStatusConstants {

    /**
     * 访问成功
     */
    const val accessSuccess: Int = 200

    /**
     * 服务器错误
     */
    const val serviceException: Int = 500

    /**
     * 未知服务器错误
     */
    const val unknownServiceException: Int = 407

    /**
     * 连接错误
     */
    const val connectException: Int = 403

    /**
     * socket连接超时错误
     */
    const val socketTimeoutException: Int = 408

    /**
     * socket错误
     */
    const val socketException: Int = 505

    /**
     * 未标识错误
     */
    const val unknownException: Int = 999999

    /**
     * 数据解析错误
     */
    const val jsonSyntaxException: Int = 1000

    const val sslHandshakeException: Int = 1001
}