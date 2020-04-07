package com.lucers.http

/**
 * BundleConstants
 */
class HttpConstants {

    companion object {
        /**
         * 请求成功
         */
        const val success: Int = 0

        /**
         * 访问成功
         */
        const val accessSuccess: Int = 200

        /**
         * 未知服务器错误
         */
        const val unknownServiceException: Int = 403

        /**
         * 连接错误
         */
        const val connectException: Int = 404

        /**
         * socket连接超时错误
         */
        const val socketTimeoutException: Int = 504

        /**
         * socket错误
         */
        const val socketException: Int = 505

        /**
         * 未标识错误
         */
        const val unknownException: Int = 999999
    }
}